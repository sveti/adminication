
package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Lesson;
import f54148.adminication.service.LessonService;

@Controller
public class LessonController {

	@Autowired
	LessonService lessonservice;

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

}
