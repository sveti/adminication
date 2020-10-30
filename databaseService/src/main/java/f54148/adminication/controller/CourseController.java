package f54148.adminication.controller;

import java.util.ArrayList;
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
import f54148.adminication.entity.CourseDetails;
import f54148.adminication.entity.Schedule;
import f54148.adminication.entity.Student;
import f54148.adminication.entity.Teacher;
import f54148.adminication.service.CourseDetailsService;
import f54148.adminication.service.CourseService;
import f54148.adminication.service.ScheduleService;


@Controller
public class CourseController {
	
	@Autowired 
	private CourseService courseService;

	@Autowired 
	private CourseDetailsService cdService;
	
	@Autowired 
	private ScheduleService sService;
	
	@PostMapping(path="/addC")
	  public @ResponseBody String addNewCourse (@RequestBody Course course) {
		
		//get schedule and courses from passed argument
		List<Schedule>shedule = course.getCourseSchedule();
		List<CourseDetails>details = course.getDetails();
		
		List<Schedule>pshedule = new ArrayList<Schedule>();
		List<CourseDetails>pdetails = new ArrayList<CourseDetails>();
		
		course.setCourseSchedule(null);
		course.setDetails(null);
		
		//persist details first 
		for(CourseDetails cd : details) {
			if(cd.getId()==null) {
				cdService.addCourseDetails(cd);
			}
			pdetails.add(cd);
		}
			
		//persist schedule
		for(Schedule s : shedule) {
			if(s.getId()==null) {
				sService.addSchedule(s);
			}
			pshedule.add(s);
		}
		
		//add course to database
		if(courseService.addCourse(course)) {
			//add schedule and details and then persist
			course.setCourseSchedule(pshedule);
			course.setDetails(pdetails);
			updateCourse(course);
			return "Added course!";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@GetMapping(path="/courses")
	  public @ResponseBody List<Course> getAllCourses() {
	    return courseService.getCourses();
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
	@GetMapping(path="/course/{id}/teachers")
	  public @ResponseBody List<Teacher> getTeachersByCourseId(@PathVariable("id") Long id) {
	    return courseService.getTeachersByCourseId(id);
	    }
	
	@GetMapping(path="/course/{id}/schedule")
	  public @ResponseBody List<Schedule> getScheduleByCourseId(@PathVariable("id") Long id) {
	    return courseService.getScheduleByCourseId(id);
	    }
	
	
	

}
