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
		  List<Parent> parentsList = new ArrayList<>();
		  parentRepository.findAll().forEach(parentsList::add);
		  return parentsList;
		 }

	 	public Parent getParentById(Long parentId) {
		  Optional<Parent> opParent = parentRepository.findById(parentId);
		  if (opParent.isPresent()) {
		   return opParent.get();
		  } else {
		   return null;
		  }
		 }
		 
	 	public boolean addStudent(Long parentId, Long childId) {
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

		 public boolean addParent(Parent parent) {
		  if (parentRepository.save(parent) != null) {
		   return true;
		  } else {
		   return false;
		  }
		 }
		 
		 public boolean updateParent(Parent parent) {
			  if (parentRepository.save(parent) != null) {
			   return true;
			  } else {
			   return false;
			  }
			 }

		 public boolean deleteParent(Long parentId) {
		  if (parentRepository.findById(parentId) != null) {
			  parentRepository.deleteById(parentId);
		   return true;
		  } else {
		   return false;
		  }
		 }
	

}
