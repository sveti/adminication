package f54148.adminication.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.LessonDTO;
import f54148.adminication.service.LessonService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

	private final LessonService lessonService;
	

	@GetMapping(path = "/{lessonId}")
	public @ResponseBody LessonDTO getLessonDTOById(@PathVariable("lessonId") @Min(1) Long lessonId) {
		return lessonService.getLessonDTO(lessonId);
	}
	
	@GetMapping(path = "/{teacherId}/{courseId}")
	public @ResponseBody List<LessonDTO> getUpcommingCoursesByTeacherId(@PathVariable("teacherId") @Min(1) Long teacherId,@PathVariable("courseId") @Min(1) Long courseId) {
		return lessonService.getLessonsByTeacherIdAndCourseId(teacherId, courseId);
	}
	
	@PostMapping(path = "/add")
	public @ResponseBody String addLessonDTO(@RequestBody LessonDTO lessonDTO) {
		return lessonService.addLesson(lessonDTO);
	}

}
