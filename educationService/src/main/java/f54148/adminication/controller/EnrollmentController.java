package f54148.adminication.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.AddEnrollmentDTO;
import f54148.adminication.service.EnrollmentService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentController {
	
	private final EnrollmentService enrollmentService;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addEnrollmentDTO(@RequestBody AddEnrollmentDTO dto) {
		return enrollmentService.addEnrollment(dto);
	}
	
	@DeleteMapping(path = "/delete/{studentId}/{courseId}")
	public @ResponseBody String deleteEnrollmentByStudentAndCourse(@PathVariable("studentId") Long studentId,@PathVariable("courseId") Long courseId) {
		return enrollmentService.deleteEnrollmentByStudentAndCourse(studentId,courseId);
	}


}
