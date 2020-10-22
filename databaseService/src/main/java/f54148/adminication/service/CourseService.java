package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	
	 public List<Course> getCourses() {
		  List<Course> courseList = new ArrayList<>();
		  courseRepository.findAll().forEach(courseList::add);
		  return courseList;
		 }

		 public Course getCourseById(Long courseId) {
		  Optional<Course> opCourse = courseRepository.findById(courseId);
		  if (opCourse.isPresent()) {
		   return opCourse.get();
		  } else {
		   return null;
		  }
		 }

		 public boolean addCourse(Course course) {
		  if (courseRepository.save(course) != null) {
		   return true;
		  } else {
		   return false;
		  }
		 }
		 
		 public boolean updateCourse(Course course) {
			  if (courseRepository.save(course) != null) {
			   return true;
			  } else {
			   return false;
			  }
			 }

		 public boolean deleteCourse(Long courseId) {
		  if (courseRepository.findById(courseId) != null) {
			  courseRepository.deleteById(courseId);
		   return true;
		  } else {
		   return false;
		  }
		 }
	

	
	
}
