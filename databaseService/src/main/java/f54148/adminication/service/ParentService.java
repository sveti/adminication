package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Parent;
import f54148.adminication.entity.Student;

import f54148.adminication.repository.ParentRepository;
import f54148.adminication.repository.StudentRepository;

@Service
public class ParentService {
	@Autowired
	private ParentRepository parentRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	 public List<Parent> getParents() {
		  List<Parent> userList = new ArrayList<>();
		  parentRepository.findAll().forEach(userList::add);
		  return userList;
		 }

		 public Parent getParentById(Integer userId) {
		  Optional<Parent> opUser = parentRepository.findById(userId);
		  if (opUser.isPresent()) {
		   return opUser.get();
		  } else {
		   return null;
		  }
		 }
		 
		 public boolean addStudent(Integer parentId, Integer childId) {
			  Optional<Parent> opParent = parentRepository.findById(parentId);
			  Optional<Student> opChild = studentRepository.findById(childId);
			  
			  if (opParent.isPresent() && opChild.isPresent()) {
				  Parent p =  opParent.get();
				  Student s = opChild.get();
				  p.addChild(s);
				  s.setParent(p);
				  parentRepository.save(p);
				  studentRepository.save(s);
				  return true;
			  } else {
			   return false;
			  }
			 }

		 public boolean addParent(Parent user) {
		  if (parentRepository.save(user) != null) {
		   return true;
		  } else {
		   return false;
		  }
		 }
		 
		 public boolean updateParent(Parent user) {
			  if (parentRepository.save(user) != null) {
			   return true;
			  } else {
			   return false;
			  }
			 }

		 public boolean deleteParent(Integer userId) {
		  if (parentRepository.findById(userId) != null) {
			  parentRepository.deleteById(userId);
		   return true;
		  } else {
		   return false;
		  }
		 }
	

}
