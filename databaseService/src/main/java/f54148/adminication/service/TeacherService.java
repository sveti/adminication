package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.Teacher;
import f54148.adminication.entity.Teaching;
import f54148.adminication.repository.TeacherRepository;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;
	
	
	public List<Teacher> getTeachers() {
		  List<Teacher> teachersList = new ArrayList<>();
		  teacherRepository.findAll().forEach(teachersList::add);
		  return teachersList;
		 }

	public Teacher getTeacherById(Long teacherId) {
		  Optional<Teacher> opTeacher = teacherRepository.findById(teacherId);
		  if (opTeacher.isPresent()) {
		   return opTeacher.get();
		  } else {
		   return null;
		  }
		 }
	
	
	
	public boolean addTeacher(Teacher teacher) {
		  if (teacherRepository.save(teacher) != null) {
		   return true;
		  } else {
		   return false;
		  }
		 }

		 public boolean deleteTeacher(Long teacherId) {
		  if (teacherRepository.findById(teacherId) != null) {
			  teacherRepository.deleteById(teacherId);
		   return true;
		  } else {
		   return false;
		  }
		 }
		 
		 
		 
		 
		 public boolean updateTeacher(Teacher t) {
			 if (teacherRepository.save(t)!= null) {
				 
				   return true;
				  } else {
				   return false;
				  }
		}

		public List<Course> getCoursesByTeacherId(Long teacherId) {
			 Optional<Teacher> opTeacher = teacherRepository.findById(teacherId);
			  if (opTeacher.isPresent()) {
			   Teacher t =  opTeacher.get();
			   
			   List<Course> courses = new ArrayList<Course>();
			   
			   for(Teaching teach : t.getTeaching() ) {
				   
				   courses.add(teach.getCourse());
			   }
			   return courses;
			   
			  } else {
			   return null;
			  }
		}
	
}
