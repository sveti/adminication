package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.StudentScheduleDTO;
import f54148.adminication.entity.Attendance;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseWaitingList;
import f54148.adminication.entity.Enrollment;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventSignUp;
import f54148.adminication.entity.EventWaitingList;
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

	public List<StudentScheduleDTO> getStudentCourseSchedule(Long studentId) {
		
		
		List<StudentScheduleDTO> dtoList = new ArrayList<>();
		List<Course> signedUpCourses = getCoursesStudentById(studentId);
		
		for(Course c: signedUpCourses) {
			for(Schedule s:c.getCourseSchedule()) {
				dtoList.add(convertToStudentScheduleDTO(s));
			}
		}
		
		return dtoList;
	}
	
}
