package f54148.adminication.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.dto.AddEventWaitingListDTO;
import f54148.adminication.dto.EventOfStudentInWaitingListDTO;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventWaitingList;
import f54148.adminication.entity.Student;
import f54148.adminication.service.EventWaitingListService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/events/eventWaitingLists")
public class EventWaitingListController {

	private final EventWaitingListService eventWaitingListService;

	@PostMapping(path = "/addEventWaitingList")
	public @ResponseBody String addNewEventWaitingList(@RequestBody EventWaitingList eventWaitingList) {

		if (eventWaitingListService.addEventWaitingList(eventWaitingList)) {
			return "Saved eventWaitingList";
		} else {
			return "An error has occurred";
		}

	}
	
	@PostMapping(path = "/add")
	public @ResponseBody String addWaitingListSignUp(@RequestBody AddEventWaitingListDTO dto) {

		if (eventWaitingListService.addWaitingListSignUp(dto)) {
			return "Successfully added student to event waiting list!";
		} else {
			return "An error has occurred";
		}

	}

	@PutMapping(path = "/updateEventWaitingList")
	public @ResponseBody String updateEventWaitingList(@RequestBody EventWaitingList eventWaitingList) {

		if (eventWaitingListService.addEventWaitingList(eventWaitingList)) {
			return "Updated eventWaitingList";
		} else {
			return "An error has occurred";
		}

	}

	@GetMapping(path = "/eventWaitingList/{id}")
	public @ResponseBody EventWaitingList getEventWaitingListById(@PathVariable("id") Long id) {
		return eventWaitingListService.getEventWaitingListById(id);
	}
	@GetMapping(path = "/eventWaitingList/{id}/student")
	public @ResponseBody Student getStudentByEventWaitingListId(@PathVariable("id") Long id) {
		return eventWaitingListService.getStudentByEventWaitingListId(id);
	}

	@GetMapping(path = "/eventWaitingList/{id}/event")
	public @ResponseBody Event getEventByEventWaitingListId(@PathVariable("id") Long id) {
		return eventWaitingListService.getEventByEventWaitingListId(id);
	}


	@GetMapping(path = "/eventWaitingLists")
	public @ResponseBody List<EventWaitingList> getAllEventWaitingLists() {
		return eventWaitingListService.getEventWaitingLists();
	}

	@GetMapping(path = "/eventWaitingList/{eventId}/firstInQueue")
	public @ResponseBody Student getFirstStudentInQueue(@PathVariable("eventId") Long eventId) {
		return eventWaitingListService.getFirstStudentInQueue(eventId);
	}

	@DeleteMapping(path = "/eventWaitingList/{id}")
	public @ResponseBody String deteleEventWaiting(@PathVariable("id") Long eventWaitingListId) {
		if (eventWaitingListService.deleteEventWaitingList(eventWaitingListId)) {
			return "Deleted eventWaitingList";
		} else {
			return "An error has occurred";
		}
	}

	@GetMapping(path = "/student/{studentId}/waitingList")
	public @ResponseBody List<EventOfStudentInWaitingListDTO> getEventWaitingListOfStudent(@PathVariable("studentId") Long studentId) {
		return eventWaitingListService.getWaitingListsOfStudent(studentId);
	}
	
}
