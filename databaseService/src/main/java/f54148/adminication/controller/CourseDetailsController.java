package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.CourseDetails;
import f54148.adminication.service.CourseDetailsService;

@Controller
public class CourseDetailsController {

	@Autowired
	CourseDetailsService service;
	
	@PostMapping(path="/addCD")
	  public @ResponseBody String addNewCourseDetails (@RequestBody CourseDetails courseDetails) {
		
		if(service.addCourseDetails(courseDetails)) {
			return "Saved courseDetails";
		}
		else {
			return "An error has occured";
		}
	    
	  }

	@GetMapping(path="/courseDetail/{id}")
	  public @ResponseBody CourseDetails getCourseById(@PathVariable("id") Long courseId) {
	    return service.getCourseDetailsById(courseId);
	    }
	
	@GetMapping(path="/courseDetails")
	  public @ResponseBody List<CourseDetails> getAllCourses() {
	    return service.getCourseDetails();
	  }
	
}
