package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Student;
import f54148.adminication.repository.StudentRepository;


@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	 public List<Student> getStudents() {
		  List<Student> userList = new ArrayList<>();
		  studentRepository.findAll().forEach(userList::add);
		  return userList;
		 }

		 public Student getStudentById(Integer userId) {
		  Optional<Student> opUser = studentRepository.findById(userId);
		  if (opUser.isPresent()) {
		   return opUser.get();
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
			 
			 public boolean updateStudent(Student user) {
				  if (studentRepository.save(user) != null) {
				   return true;
				  } else {
				   return false;
				  }
				 }

			 public boolean deleteParent(Integer userId) {
			  if (studentRepository.findById(userId) != null) {
				  studentRepository.deleteById(userId);
			   return true;
			  } else {
			   return false;
			  }
			 }

}
