package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.StudentGradesDTO;
import f54148.adminication.entity.Course;
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
			}
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
	}
	

}
