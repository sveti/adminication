package f54148.adminication.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.AddCourseDTO;
import f54148.adminication.dto.MonthlyTeacherSalaryDTO;
import f54148.adminication.service.CourseService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/courses")
public class CourseController {
	
	private final CourseService courseService;
	
	@PostMapping(path = "/add")
	public @ResponseBody String addNewCourse(@RequestBody AddCourseDTO course) {
		return courseService.add(course);
	}

}
