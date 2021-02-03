
package f54148.adminication.controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.dto.LessonDTO;
import f54148.adminication.entity.Attendance;
import f54148.adminication.entity.Lesson;
import f54148.adminication.entity.Student;
import f54148.adminication.service.LessonService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/courses/lessons")
public class LessonController {

	private final LessonService lessonservice;

	@PostMapping(path = "/addLesson")
	public @ResponseBody String addLesson(@RequestBody Lesson lesson) {

		if (lessonservice.addLesson(lesson)) {
			return "Added lesson";
		} else {
			return "An error has occured";
		}

	}

	@PutMapping(path = "/updateLesson")
	public @ResponseBody String updateLesson(@RequestBody Lesson lesson) {

		if (lessonservice.updateLesson(lesson)) {
			return "Updated lesson";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/lesson/{id}")
	public @ResponseBody Lesson getLessonById(@PathVariable("id") Long id) {
		return lessonservice.getLessonById(id);
	}
	

	@GetMapping(path = "/{id}/display")
	public @ResponseBody LessonDTO getLessonDTOById(@PathVariable("id") Long id) {
		return lessonservice.getLessonDTOById(id);
	}
	
	@PostMapping(path = "/add")
	public @ResponseBody String addLessonDTO(@RequestBody LessonDTO lessonDTO) {
		if( lessonservice.addLessonDTO(lessonDTO)) {
			return "Saved lesson successfully!";
		}
		else {
			return "An error has occured!";
		}
	}
	
	
	@GetMapping(path = "/lesson/{id}/attendances")
	public @ResponseBody Set<Attendance> getAttendancesByLessonId(@PathVariable("id") Long id) {
		return lessonservice.getAttendancesByLessonId(id);
	}
	
	@GetMapping(path = "/lesson/{id}/attendances/present")
	public @ResponseBody Set<Student> getPresentAttendancesByLessonId(@PathVariable("id") Long id) {
		return lessonservice.getPresentAttendancesByLessonId(id);
	}

	@GetMapping(path = "/lesson/{id}/attendances/missing")
	public @ResponseBody Set<Student> getMissingAttendancesByLessonId(@PathVariable("id") Long id) {
		return lessonservice.getMissingAttendancesByLessonId(id);
	}
	@DeleteMapping(path = "/lesson/{id}")
	public @ResponseBody String deleteLessonById(@PathVariable("id") Long id) {
		if (lessonservice.deleteLesson(id)) {
			return "Deleted lesson!";
		} else {
			return "An error has occured";
		}
	}

	@GetMapping(path = "/lessons")
	public @ResponseBody List<Lesson> getAllLessons() {
		return lessonservice.getLessons();
	}
	
	@GetMapping(path = "/{idTeacher}/{idCourse}")
	public @ResponseBody List<LessonDTO> getLessonsByTeacherIdAndCourseId(@PathVariable("idTeacher") Long idTeacher,@PathVariable("idCourse") Long idCourse) {
		return lessonservice.getLessonsByTeacherIdAndCourseId(idTeacher,idCourse);
	}
	
	

}
