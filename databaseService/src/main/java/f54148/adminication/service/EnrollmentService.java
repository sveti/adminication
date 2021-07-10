package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.AddEnrollmentDTO;
import f54148.adminication.dto.StudentGradesDTO;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.Draft;
import f54148.adminication.entity.Enrollment;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.EnrollmentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnrollmentService {

	
	private final EnrollmentRepository enrollmentRepository;

	private final StudentService studentService;

	private final CourseService courseService;
	
	private final DraftService draftService;
	
	private final NotificationService notificationService;
	
	private final ModelMapper modelMapper;

	public List<Enrollment> getEnrollments() {
		List<Enrollment> enrollmentList = new ArrayList<>();
		enrollmentRepository.findAll().forEach(enrollmentList::add);
		return enrollmentList;
	}
	
	public List<Enrollment> getEnrollmentsByCourseId(Long courseId) {
		List<Enrollment> enrollmentList = new ArrayList<>();
		enrollmentRepository.findAll().forEach(enrollmentList::add);
		
		List<Enrollment> courseEnrollment = new ArrayList<>();
		for(Enrollment e:enrollmentList) {
			if(e.getCourse().getId()==courseId) {
				courseEnrollment.add(e);
			}
		}
		
		return courseEnrollment;
	}

	public Enrollment getEnrollmentById(Long enrollmentId) {
		Optional<Enrollment> opEnrollment = enrollmentRepository.findById(enrollmentId);
		if (opEnrollment.isPresent()) {
			return opEnrollment.get();
		} else {
			return null;
		}
	}

	public boolean addEnrollment(Enrollment enrollment) {
		if (enrollmentRepository.save(enrollment) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addEnrollmentDTO(AddEnrollmentDTO enrollment) {
		
		Enrollment enroll = new Enrollment();
		enroll.setStudent(studentService.getStudentById(enrollment.getStudentId()));
		enroll.setCourse(courseService.getCourseById(enrollment.getCourseId()));
		if( addEnrollment(enroll)) {
			
			String message = enroll.getStudent().getName() + " " + enroll.getStudent().getLastName() + " has been successfully enrolled in course #" + enroll.getCourse().getId() + " : " + enroll.getCourse().getTitle();
			Long draftId = draftService.createDraftFromAdmin(message);
			Long parentId = enroll.getStudent().getParent().getId();
			notificationService.sendDraft(draftId, parentId);
			
			return true;
		}
		else {
			return false;
		}
	}

	public boolean updateEnrollment(Enrollment enrollment) {
		if (enrollmentRepository.save(enrollment) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteEnrollment(Long enrollmentId) {
		Optional<Enrollment> opEnrollment = enrollmentRepository.findById(enrollmentId);
		if (opEnrollment.isPresent()) {
			Enrollment e = opEnrollment.get();

			Student s = e.getStudent();

			s.getEnrollments().remove(e);
			studentService.updateStudent(s);

			Course c = e.getCourse();
			c.getEnrollments().remove(e);

			courseService.updateCourse(c);
			enrollmentRepository.deleteById(enrollmentId);
			
			Student nextS = courseService.updateWaitinList(c);
			
			if(nextS!=null) {
				
				Enrollment newE = new Enrollment(nextS,c);
				addEnrollment(newE);
				String message = nextS.getName() + " " + nextS.getLastName() + " has been successfully enrolled in course #" + c.getId() + " : " + c.getTitle();
				Long draftId = draftService.createDraftFromAdmin(message);
				Long parentId = nextS.getParent().getId();
				notificationService.sendDraft(draftId, parentId);
				String studentMessage = "You have been successfully enrolled in course #" + c.getId() + " : " + c.getTitle();
				Long studentdraftId = draftService.createDraftFromAdmin(studentMessage);
				notificationService.sendDraft(studentdraftId, nextS.getId());
				
				}
			
			
			return true;
		} else {
			return false;
		}
	}

	public Student getStudentByEnrollmentById(Long enrollmentId) {
		Optional<Enrollment> opEnrollment = enrollmentRepository.findById(enrollmentId);
		if (opEnrollment.isPresent()) {
			return opEnrollment.get().getStudent();
		} else {
			return null;
		}
	}

	public Course getCourseByEnrollmentById(Long enrollmentId) {
		Optional<Enrollment> opEnrollment = enrollmentRepository.findById(enrollmentId);
		if (opEnrollment.isPresent()) {
			return opEnrollment.get().getCourse();
		} else {
			return null;
		}
	}
	
	public StudentGradesDTO convertToStudentAttendanceDTO(Enrollment enrollment) {
		
		StudentGradesDTO studentDTO =  modelMapper.map(enrollment, StudentGradesDTO.class);
		return studentDTO;
	}
	
	public List<StudentGradesDTO> getStudentGradesDTOOfCourse(Long courseId){
			
		List<Enrollment> allEnrollments = getEnrollmentsByCourseId(courseId);
		
		List<StudentGradesDTO> students = new ArrayList<>();
		
		for(Enrollment e: allEnrollments) {
			
			students.add(convertToStudentAttendanceDTO(e));
		}
		
		return students;
		
	}
	
	public Enrollment convertToEnrollments(StudentGradesDTO dto) {
		Enrollment enrollment =  modelMapper.map(dto, Enrollment.class);
		enrollment.setStudent(studentService.getStudentById(dto.getStudentId()));
		enrollment.setCourse(courseService.getCourseById(dto.getCourseId()));
		return enrollment;
	
	}

	public boolean updateStudentGrades(List<StudentGradesDTO> studentsGrades) {

	List <Enrollment> enrollments = new ArrayList<>();
		
		for(StudentGradesDTO dto: studentsGrades) {
			
			enrollments.add(convertToEnrollments(dto));
		
		}
		
		try {
			for(Enrollment e: enrollments) {
				
				updateEnrollment(e);
	
				String message = e.getStudent().getName() + " " + e.getStudent().getLastName() + " has received the grade " + e.getGrade() +" in course #" + e.getCourse().getId() + " : " + e.getCourse().getTitle();
				Long draftId = draftService.createDraftFromAdmin(message);
				Long parentId = e.getStudent().getParent().getId();
				notificationService.sendDraft(draftId, parentId);
				String studentMessage = "You have been graded in course #" + e.getCourse().getId() + " : " + e.getCourse().getTitle() + " with grade " + e.getGrade();
				Long studentdraftId = draftService.createDraftFromAdmin(studentMessage);
				notificationService.sendDraft(studentdraftId, e.getStudent().getId());
				
			}
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
	}

	private Enrollment findEnrollmentByStudentAndCourse(Long studentId, Long courseId) {
		
		Student s = studentService.getStudentById(studentId);
		Course c = courseService.getCourseById(courseId);
		
		Enrollment e = enrollmentRepository.findByStudentAndCourse(s,c);
		
		return e;	
	}
	
	public boolean deleteEnrollmentByStudentAndCourse(Long studentId, Long courseId) {
		
		Enrollment e = findEnrollmentByStudentAndCourse(studentId,courseId);
		
		return deleteEnrollment(e.getId());
	}


	

}
