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

import f54148.adminication.entity.Course;
import f54148.adminication.entity.Student;
import f54148.adminication.service.CourseService;


@Controller
public class CourseController {
	
	@Autowired 
	private CourseService courseService;
	
	@PostMapping(path="/addC")
	  public @ResponseBody String addNewCourse (@RequestBody Course course) {
		
		if(courseService.addCourse(course)) {
			return "Saved course";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@PutMapping(path="/updateC")
	  public @ResponseBody String updateCourse (@RequestBody Course course) {
		
		if(courseService.addCourse(course)) {
			return "Updated course";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@GetMapping(path="/course/{id}")
	  public @ResponseBody Course getCourseById(@PathVariable("id") Long id) {
	    return courseService.getCourseById(id);
	    }
	
	@GetMapping(path="/course/{id}/students")
	  public @ResponseBody List<Student> getStudentsByCourseId(@PathVariable("id") Long id) {
	    return courseService.getStudentsByCourseId(id);
	    }
	
	@GetMapping(path="/courses")
	  public @ResponseBody List<Course> getAllCourses() {
	    return courseService.getCourses();
	  }

}
