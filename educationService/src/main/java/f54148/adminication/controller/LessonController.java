package f54148.adminication.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.AttendanceDTO;
import f54148.adminication.dto.LessonDTO;
import f54148.adminication.dto.UpdateLessonDescriptionDTO;
import f54148.adminication.service.AttendanceService;
import f54148.adminication.service.LessonService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

	private final LessonService lessonService;
	private final AttendanceService attedanceService;

	@GetMapping(path = "/{lessonId}")
	@RolesAllowed({"PARENT","TEACHER","STUDENT"})
	public @ResponseBody LessonDTO getLessonDTOById(@PathVariable("lessonId") @Min(1) Long lessonId) {
		return lessonService.getLessonDTO(lessonId);
	}
	
	@GetMapping(path = "/{courseId}/lessons")
	@RolesAllowed({"PARENT","TEACHER","STUDENT"})
	public @ResponseBody List<LessonDTO> getLessonsByCourseId(@PathVariable("courseId") @Min(1) Long courseId) {
		return lessonService.getLessonsByCourseId(courseId);
	}
	
	@PostMapping(path = "/add")
	@RolesAllowed({"TEACHER"})
	public @ResponseBody String addLessonDTO(@RequestBody LessonDTO lessonDTO) {
		return lessonService.addLesson(lessonDTO);
	}

	@GetMapping(path = "/{lessonId}/attendance")
	@RolesAllowed({"PARENT","TEACHER","STUDENT"})
	public @ResponseBody List<AttendanceDTO> getAttendanceDTOByLessonId(@PathVariable("lessonId") @Min(1) Long lessonId) {
		return attedanceService.getAttendancesOfLesson(lessonId);
	}
	
	@PutMapping(path = "/updateDescription")
	@RolesAllowed({"TEACHER"})
	public @ResponseBody String updateDescription(@RequestBody UpdateLessonDescriptionDTO updateLessonDTO) {
		return lessonService.updateLessonDescription(updateLessonDTO);
	}
}
