package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseDetails;
import f54148.adminication.repository.CourseDetailsRepository;

@Service
public class CourseDetailsService {
	
	@Autowired
	private CourseDetailsRepository repo;
	
	
	 public List<CourseDetails> getCourseDetails() {
		  List<CourseDetails> courseDetailsList = new ArrayList<>();
		  repo.findAll().forEach(courseDetailsList::add);
		  return courseDetailsList;
		 }

		 public CourseDetails getCourseDetailsById(Long courseId) {
		  Optional<CourseDetails> opcourseDetails = repo.findById(courseId);
		  if (opcourseDetails.isPresent()) {
		   return opcourseDetails.get();
		  } else {
		   return null;
		  }
		 }
		 
		public List<Course> getCourses(Long courseId){
			Optional<CourseDetails> opcourseDetails = repo.findById(courseId);
			  if (opcourseDetails.isPresent()) {
			   return opcourseDetails.get().getCourses();
			  } else {
			   return null;
			  }
		}

		 public boolean addCourseDetails(CourseDetails courseDetails) {
		  if (repo.save(courseDetails) != null) {
		   return true;
		  } else {
		   return false;
		  }
		 }
		 
		 public boolean updateCourseDetails(CourseDetails courseDetails) {
			  if (repo.save(courseDetails) != null) {
			   return true;
			  } else {
			   return false;
			  }
			 }

		 public boolean deleteCourseDetails(Long courseDetailsId) {
		  if (repo.findById(courseDetailsId) != null) {
			  repo.deleteById(courseDetailsId);
		   return true;
		  } else {
		   return false;
		  }
		 }
	
	
	

}
