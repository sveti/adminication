package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.Enrollment;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventWaitingList;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

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

	public boolean deleteParent(Long userId) {
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
			Student s =  opUser.get();
			List<Course> courses = new ArrayList<Course>();
			for(Enrollment e :s.getEnrollments()) {
				
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
			return opStudent.get().getEvents();
			
		} else {
			return null;
		}
	}

	public List<Event> getStudentWaitingEvents(Long studentId) {
		Optional<Student> opUser = studentRepository.findById(studentId);
		if (opUser.isPresent()) {
			Student s =  opUser.get();
			List<Event> events = new ArrayList<Event>();
			for(EventWaitingList e :s.getEventWaitingList()) {
				events.add(e.getEvent());
			}
			return events;
			
		} else {
			return null;
		}
	}

}
