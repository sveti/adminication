package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseDetail;
import f54148.adminication.service.CourseDetailService;

@Controller
public class CourseDetailController {

	@Autowired
	CourseDetailService service;

	@PostMapping(path = "/addCourseDetail")
	public @ResponseBody String addNewCourseDetail(@RequestBody CourseDetail courseDetails) {

		if (service.addCourseDetail(courseDetails)) {
			return "Saved courseDetails";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/courseDetail/{id}")
	public @ResponseBody CourseDetail getCourseById(@PathVariable("id") Long courseDetailsId) {
		return service.getCourseDetailsById(courseDetailsId);
	}

	@DeleteMapping(path = "/courseDetail/{id}")
	public @ResponseBody String deleteCourseDetailsById(@PathVariable("id") Long courseDetailsId) {
		if (service.deleteCourseDetails(courseDetailsId)) {
			return "Deleted course details!";
		} else {
			return "An error has occured!";
		}
	}

	@GetMapping(path = "/courseDetail/{id}/courses")
	public @ResponseBody List<Course> getAllCoursesByDetail(@PathVariable("id") Long courseDetailsId) {
		return service.getCourses(courseDetailsId);
	}

	@GetMapping(path = "/courseDetails")
	public @ResponseBody List<CourseDetail> getAllCourses() {
		return service.getCourseDetails();
	}

}
