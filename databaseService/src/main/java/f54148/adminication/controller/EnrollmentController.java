package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Enrollment;
import f54148.adminication.service.EnrollmentService;

@Controller
public class EnrollmentController {
	
	@Autowired
	EnrollmentService enrollmentservice;
	
	@PostMapping(path="/enrollment")
	  public @ResponseBody String addEnrollment (@RequestBody Enrollment enrollment) {
		
		if(enrollmentservice.addEnrollment(enrollment)) {
			return "Enrolled";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@PutMapping(path="/updateEnrollment")
	  public @ResponseBody String updateCourse (@RequestBody Enrollment enrollment) {
		
		if(enrollmentservice.updateEnrollment(enrollment)) {
			return "Updated enrollment";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@GetMapping(path="/enrollment/{id}")
	  public @ResponseBody Enrollment getCourseById(@PathVariable("id") Long id) {
	    return enrollmentservice.getEnrollmentById(id);
	    }
	
	@GetMapping(path="/enrollments")
	  public @ResponseBody List<Enrollment> getAllEnrollments() {
	    return enrollmentservice.getEnrollments();
	  }

	

}
