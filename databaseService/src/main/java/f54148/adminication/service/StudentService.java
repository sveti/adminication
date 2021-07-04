package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.GradesOfStudentDTO;
import f54148.adminication.dto.MonthlyTeacherSalaryDTO;
import f54148.adminication.dto.StudentAttendanceReportDTO;
import f54148.adminication.dto.StudentMonthlyAttendanceDTO;
import f54148.adminication.dto.StudentScheduleDTO;
import f54148.adminication.entity.Attendance;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseStatus;
import f54148.adminication.entity.CourseWaitingList;
import f54148.adminication.entity.Enrollment;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventSignUp;
import f54148.adminication.entity.EventWaitingList;
import f54148.adminication.entity.Lesson;
import f54148.adminication.entity.Schedule;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.StudentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	private final ModelMapper modelMapper;
	//private final PasswordEncoder encoder  = new BCryptPasswordEncoder();

	public List<Student> getStudents() {
		List<Student> studentList = new ArrayList<>();
		studentRepository.findAll().forEach(studentList::add);
		return studentList;
	}

	public Student getStudentById(Long studentId) {
		Optional<Student> opStudent = studentRepository.findById(studentId);
		if (opStudent.isPresent()) {
			return opStudent.get();
		} else {
			return null;
		}
	}

	public boolean addStudent(Student user) {
		
		//user.setPassword(encoder.encode(user.getPassword()));
		
		if (studentRepository.save(user) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateStudent(Student student) {

		if (studentRepository.save(student) != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean deleteStudent(Long userId) {
		if (studentRepository.findById(userId) != null) {
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
			List<Course> courses = new ArrayList<Course>();
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
			List<Event> events = new ArrayList<Event>();
			
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
			List<Event> events = new ArrayList<Event>();
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
			List<Course> courses = new ArrayList<Course>();
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
		if (opStudent.isPresent()) {
			return opStudent.get().getAttendances();

		} else {
			return null;
		}
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
		System.out.println("i have been called for course " + c.getTitle());
		System.out.println("i have "+ c.getLessons().size() + " lessons");
		
		List<StudentAttendanceReportDTO> dto = new ArrayList<StudentAttendanceReportDTO>();
		
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
		
		List<StudentMonthlyAttendanceDTO> dto = new ArrayList<StudentMonthlyAttendanceDTO>();
		
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
	
}
