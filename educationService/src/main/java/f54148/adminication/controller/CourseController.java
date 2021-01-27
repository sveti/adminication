package f54148.adminication.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.CourseWithDetailsDTO;
import f54148.adminication.dto.StartedCourseDTO;
import f54148.adminication.dto.UpcommingCourseDTO;
import f54148.adminication.service.CourseService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/courses")
public class CourseController {

	private final CourseService courseService;
	
	@GetMapping(path = "/upcomming/{courseId}")
	public @ResponseBody UpcommingCourseDTO getUpcommingCourseById(@PathVariable("courseId") @Min(1) Long courseId) {
		return courseService.getUpcommingCourseOfTeacher(courseId);
	}
	
	@GetMapping(path = "/{teacherId}/upcomming")
	public @ResponseBody List<UpcommingCourseDTO> getUpcommingCoursesByTeacherId(@PathVariable("teacherId") @Min(1) Long teacherId) {
		return courseService.getUpcommingCoursesOfTeacher(teacherId);
	}

	@GetMapping(path = "/details/{courseId}")
	public @ResponseBody CourseWithDetailsDTO getCourseWithDetailsDTOById(@PathVariable("courseId") @Min(1) Long courseId) {
		return courseService.getCourseWithDetails(courseId);
	}
	
	@GetMapping(path = "/started/{courseId}")
	public @ResponseBody StartedCourseDTO getStartedCourseById(@PathVariable("courseId") @Min(1) Long courseId) {
		return courseService.getStartedCourse(courseId);
	}
	
	@GetMapping(path = "/{teacherId}/started")
	public @ResponseBody List<StartedCourseDTO> getStartedCoursesByTeacherId(@PathVariable("teacherId") @Min(1) Long teacherId) {
		return courseService.getStartedCoursesOfTeacher(teacherId);
	}
	
	@GetMapping(path = "/{teacherId}/sub/started")
	public @ResponseBody List<StartedCourseDTO> getSubStartedCoursesByTeacherId(@PathVariable("teacherId") @Min(1) Long teacherId) {
		return courseService.getSubStartedCoursesByTeacherId(teacherId);
	}
	
}
