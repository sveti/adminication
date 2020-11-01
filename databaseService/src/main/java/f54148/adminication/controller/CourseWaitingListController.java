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

import f54148.adminication.entity.CourseWaitingList;
import f54148.adminication.entity.Student;
import f54148.adminication.service.CourseWaitingListService;

@Controller
public class CourseWaitingListController {

	@Autowired
	private CourseWaitingListService courseWaitingListService;

	@PostMapping(path = "/addCourseWaitingList")
	public @ResponseBody String addNewCourseWaitingList(@RequestBody CourseWaitingList courseWaitingList) {

		if (courseWaitingListService.addCourseWaitingList(courseWaitingList)) {
			return "Saved courseWaitingList";
		} else {
			return "An error has occured";
		}

	}

	@PutMapping(path = "/updateCourseWaitingList")
	public @ResponseBody String updateCourseWaitingList(@RequestBody CourseWaitingList courseWaitingList) {

		if (courseWaitingListService.addCourseWaitingList(courseWaitingList)) {
			return "Updated courseWaitingList";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/courseWaitingList/{id}")
	public @ResponseBody CourseWaitingList getCourseWaitingListById(@PathVariable("id") Long id) {
		return courseWaitingListService.getCourseWaitingListById(id);
	}

	@GetMapping(path = "/courseWaitingLists")
	public @ResponseBody List<CourseWaitingList> getAllCourseWaitingLists() {
		return courseWaitingListService.getCourseWaitingLists();
	}

	@GetMapping(path = "/courseWaitingList/{courseId}/firstInQueue")
	public @ResponseBody Student getFirstStudentInQueue(@PathVariable("courseId") Long courseId) {
		return courseWaitingListService.getFirstStudentInQueue(courseId);
	}

	@DeleteMapping(path = "/courseWaitingList/{id}")
	public @ResponseBody String deteleCourseWaiting(@PathVariable("id") Long courseWaitingListId) {
		if (courseWaitingListService.deleteCourseWaitingList(courseWaitingListId)) {
			return "Deleted courseWaitingList";
		} else {
			return "An error has occured";
		}
	}

}
