package f54148.adminication.controller;

import java.util.List;

import f54148.adminication.dto.CourseReportDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.AddCourseDTO;
import f54148.adminication.dto.CourseTitlesDTO;
import f54148.adminication.dto.DisplayEditCourseDTO;
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
	
	@GetMapping(path = "/edit/{idCourse}")
	public @ResponseBody DisplayEditCourseDTO getEditCourseDTO(@PathVariable("idCourse") Long idCourse) {
		return courseService.getEditCourseDTO(idCourse);
	}
	
	@PutMapping(path = "/edit")
	public @ResponseBody String editCourse(@RequestBody DisplayEditCourseDTO course) {
		
		return courseService.editCourse(course);
	}

	@GetMapping(path = "/titles")
	public @ResponseBody List<CourseTitlesDTO> getCourseTitles() {
		return courseService.getCourseTitles();
	}

	@GetMapping(path = "/titles/all")
	public @ResponseBody List<CourseTitlesDTO> getAllCourseTitles() {
		return courseService.getAllCourseTitles();
	}


	@GetMapping(path = "/report/{idCourse}")
	public @ResponseBody
	CourseReportDTO getCourseReport(@PathVariable("idCourse") Long idCourse) {
		return courseService.getCourseReport(idCourse);
	}



}
