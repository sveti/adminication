package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import f54148.adminication.dto.*;
import f54148.adminication.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import f54148.adminication.repository.StudentRepository;

@Service
public class StudentService {

	private final StudentRepository studentRepository;
	private final ModelMapper modelMapper;
	private final RoleService roleService;
	private final ParentService parentService;
	private final PasswordEncoder encoder  = new BCryptPasswordEncoder();


	public StudentService(StudentRepository repo, @Lazy RoleService roleService, @Lazy ModelMapper modelMapper,
						  @Lazy ParentService parentService) {
		super();
		this.studentRepository = repo;
		this.roleService = roleService;
		this.parentService = parentService;
		this.modelMapper = modelMapper;
	}

	public List<Student> getStudents() {
		List<Student> studentList = new ArrayList<>();
		studentRepository.findAll().forEach(studentList::add);
		return studentList;
	}

	public Student getStudentById(Long studentId) {
		Optional<Student> opStudent = studentRepository.findById(studentId);
		return opStudent.orElse(null);
	}

	public boolean addStudent(Student user) {
		
		//user.setPassword(encoder.encode(user.getPassword()));

		studentRepository.save(user);
		return true;
	}

	public boolean updateStudent(Student student) {

		studentRepository.save(student);
		return true;

	}

	public boolean deleteStudent(Long userId) {
		if (studentRepository.findById(userId).isPresent()) {
			studentRepository.deleteById(userId);
			return true;
		} else {
			return false;
		}
	}

	public List<Course> getCoursesStudentById(Long studentId) {
		Optional<Student> opUser = studentRepository.findById(studentId);
		if (opUser.isPresent()) {
			Student s = opUser.get();
			List<Course> courses = new ArrayList<>();
			for (Enrollment e : s.getEnrollments()) {

				courses.add(e.getCourse());

			}
			return courses;

		} else {
			return null;
		}
	}

	public List<Event> getEventsStudentById(Long studentId) {
		Optional<Student> opStudent = studentRepository.findById(studentId);
		if (opStudent.isPresent()) {
			List<Event> events = new ArrayList<>();
			
			for(EventSignUp es:opStudent.get().getEventsSignedUp()) {
				
				events.add(es.getEvent());
			}
			
			return events;

		} else {
			return null;
		}
	}

	public List<Event> getStudentWaitingEvents(Long studentId) {
		Optional<Student> opUser = studentRepository.findById(studentId);
		if (opUser.isPresent()) {
			Student s = opUser.get();
			List<Event> events = new ArrayList<>();
			for (EventWaitingList e : s.getEventWaitingList()) {
				events.add(e.getEvent());
			}
			return events;

		} else {
			return null;
		}
	}

	public List<Course> getStudentWaitingCourses(Long studentId) {
		Optional<Student> opUser = studentRepository.findById(studentId);
		if (opUser.isPresent()) {
			Student s = opUser.get();
			List<Course> courses = new ArrayList<>();
			for (CourseWaitingList c : s.getCourseWaitingList()) {
				courses.add(c.getCourse());
			}
			return courses;

		} else {
			return null;
		}
	}

	public Set<Attendance> getStudentAttendances(Long studentId) {
		Optional<Student> opStudent = studentRepository.findById(studentId);
		return opStudent.map(Student::getAttendances).orElse(null);
	}
	
	public StudentScheduleDTO convertToStudentScheduleDTO(Schedule schedule) {
		return modelMapper.map(schedule,StudentScheduleDTO.class);
	}

	public List<StudentScheduleDTO> getStudentSchedule(Long studentId) {
		
		
		List<StudentScheduleDTO> dtoList = new ArrayList<>();
		List<Course> signedUpCourses = getCoursesStudentById(studentId);
		List<Event> signedUpEvents = getEventsStudentById(studentId);
		
		for(Course c: signedUpCourses) {
			for(Schedule s:c.getCourseSchedule()) {
				dtoList.add(convertToStudentScheduleDTO(s));
			}
		}
		for(Event e: signedUpEvents) {
			for(Schedule s:e.getEventSchedule()) {
				dtoList.add(convertToStudentScheduleDTO(s));
			}
		}
		
		return dtoList;
	}
	
	private List<StudentAttendanceReportDTO> getMonthlyAttendancesOfStudent(Long studentId, Course c,Integer month, Integer year){

		List<StudentAttendanceReportDTO> dto = new ArrayList<>();
		
		for(Lesson l : c.getLessons()) {
			
			if(l.getDate().getMonthValue()== month && l.getDate().getYear()==year) {
				
				Attendance a = l.getAttendances().stream().filter(o -> o.getStudent().getId()==studentId).findFirst().get();
				
				if(a.getAttended()) {
					dto.add(new StudentAttendanceReportDTO(a.getId(),l.getDate()));
					
				}
				
			}
			
		}
		
		return dto;
	}
	
	
	public List<GradesOfStudentDTO> getGradesOfStudent(long GradesOfStudentDTO){
		
		Set<Enrollment> enrollments = getStudentById(GradesOfStudentDTO).getEnrollments();
		
		List<GradesOfStudentDTO> dto = new ArrayList<>();
		
		for(Enrollment e: enrollments) {
			if(e.getCourse().getStatus() == CourseStatus.FINISHED) {
				GradesOfStudentDTO grade = new GradesOfStudentDTO(e.getCourse().getId(),e.getCourse().getTitle(),e.getGrade());
				dto.add(grade);
			}
		}
		
		return dto;
	}

	public List<StudentMonthlyAttendanceDTO> getStudentReport(Long studentId, Integer month, Integer year) {
	
		List<Course> courses = getCoursesStudentById(studentId);
		
		courses.removeIf(course -> course.getStatus()!= CourseStatus.STARTED);
		
		List<StudentMonthlyAttendanceDTO> dto = new ArrayList<>();
		
		for(Course c: courses) {
			StudentMonthlyAttendanceDTO singleDto = new StudentMonthlyAttendanceDTO();
			singleDto.setCourseId(c.getId());
			singleDto.setCourseTitle(c.getTitle());
			singleDto.setAttendances(getMonthlyAttendancesOfStudent(studentId,c,month, year));
			singleDto.setPricePerAttendance(c.getPricePerStudent());
			dto.add(singleDto);
		}
		
		return dto;
	}

	public Student createStudent(AddStudentDTO s) {

		Student student  = modelMapper.map(s,Student.class);
		student.setPassword(encoder.encode(student.getPassword()));
		student.setRole(roleService.getRoleByName("ROLE_STUDENT"));

		return studentRepository.save(student);
	}

    public List<DisplayStudentDTO> getAllStudentsAdmin() {
		List<Student> studentList = getStudents();
		List<DisplayStudentDTO> dto = new ArrayList<>();
		for (Student student : studentList) {
			DisplayStudentDTO dtoS = new DisplayStudentDTO();
			dtoS.setLabel(student.getName() + " " + student.getLastName());
			dtoS.setValue(student.getId());
			dto.add(dtoS);
		}
		return dto;
	}


	public void addParent(Student student, Parent parent) {
		student.setParent(parent);
		studentRepository.save(student);
		Set<Student> children= parent.getChildren();
		children.add(student);
		parent.setChildren(children);
		parentService.updateParent(parent);
	}
}
