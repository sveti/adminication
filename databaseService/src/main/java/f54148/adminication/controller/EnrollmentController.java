package f54148.adminication.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.Enrollment;
import f54148.adminication.entity.Student;
import f54148.adminication.service.EnrollmentService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentController {

	private final EnrollmentService enrollmentservice;

	@PostMapping(path = "/addEnrollment")
	public @ResponseBody String addEnrollment(@RequestBody Enrollment enrollment) {

		if (enrollmentservice.addEnrollment(enrollment)) {
			return "Enrolled";
		} else {
			return "An error has occured";
		}

	}

	@PutMapping(path = "/updateEnrollment")
	public @ResponseBody String updateEnrollment(@RequestBody Enrollment enrollment) {

		if (enrollmentservice.updateEnrollment(enrollment)) {
			return "Updated enrollment";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/enrollment/{id}")
	public @ResponseBody Enrollment getEnrollmentById(@PathVariable("id") Long id) {
		return enrollmentservice.getEnrollmentById(id);
	}
	
	@GetMapping(path = "/enrollment/{id}/student")
	public @ResponseBody Student getStudentByEnrollmentById(@PathVariable("id") Long id) {
		return enrollmentservice.getStudentByEnrollmentById(id);
	}
	
	@GetMapping(path = "/enrollment/{id}/course")
	public @ResponseBody Course getCourseByEnrollmentById(@PathVariable("id") Long id) {
		return enrollmentservice.getCourseByEnrollmentById(id);
	}

	@DeleteMapping(path = "/enrollment/{id}")
	public @ResponseBody String deleteEnrollmentById(@PathVariable("id") Long id) {
		if (enrollmentservice.deleteEnrollment(id)) {
			return "Deleted enrollment!";
		} else {
			return "An error has occured!";
		}
	}

	@GetMapping(path = "/enrollments")
	public @ResponseBody List<Enrollment> getAllEnrollments() {
		return enrollmentservice.getEnrollments();
	}

}
