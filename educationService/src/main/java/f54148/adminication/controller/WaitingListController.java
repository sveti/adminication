package f54148.adminication.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.AddEventWaitingListDTO;
import f54148.adminication.dto.AddWaitingListSignUp;
import f54148.adminication.dto.CourseOfStudentInWaitingListDTO;
import f54148.adminication.dto.EventDTO;
import f54148.adminication.dto.EventOfStudentInWaitingListDTO;
import f54148.adminication.service.WaitingListService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/waitingLists")
public class WaitingListController {
	
	private final WaitingListService waitingListService;
	
	@PostMapping(path = "/add/course")
	public @ResponseBody String addSignUpToCourseWaitingList(@RequestBody AddWaitingListSignUp dto) {
		return waitingListService.addWaitingListSignUp(dto);
	}

	@GetMapping(path = "/student/{studentId}/courses")
	public @ResponseBody List<CourseOfStudentInWaitingListDTO> getWaitingListCoursesOfStudent(@PathVariable("studentId") Long studentId) {
		return waitingListService.getWaitingListCoursesOfStudent(studentId);
	}
	
	@DeleteMapping(path = "/remove/{id}/course")
	public @ResponseBody String deteleCourseWaiting(@PathVariable("id") Long courseWaitingListId) {
		return waitingListService.deleteCourseWaitingList(courseWaitingListId);
	}
	
	@GetMapping(path = "/student/{studentId}/events")
	public @ResponseBody List<EventOfStudentInWaitingListDTO> getWaitingListEventsOfStudent(@PathVariable("studentId") Long studentId) {
		return waitingListService.getWaitingListEventsOfStudent(studentId);
	}

	@PostMapping(path = "/add/event")
	public @ResponseBody String addSignUpToEventWaitingList(@RequestBody AddEventWaitingListDTO dto) {
		return waitingListService.addAddEventWaitingListSignUp(dto);
	}
	@DeleteMapping(path = "/remove/{id}/event")
	public @ResponseBody String deteleEventWaiting(@PathVariable("id") Long eventWaitingListId) {
		return waitingListService.deleteEventWaitingList(eventWaitingListId);
	}
}
