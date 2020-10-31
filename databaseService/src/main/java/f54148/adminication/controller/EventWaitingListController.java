package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.EventWaitingList;
import f54148.adminication.entity.Student;
import f54148.adminication.service.EventWaitingListService;

@Controller
public class EventWaitingListController {
	
	@Autowired 
	private EventWaitingListService eventWaitingListService;

	@PostMapping(path="/addEventWaitingList")
	  public @ResponseBody String addNewEventWaitingList (@RequestBody EventWaitingList eventWaitingList) {
		
		if(eventWaitingListService.addEventWaitingList(eventWaitingList)) {
			return "Saved eventWaitingList";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@PutMapping(path="/updateEventWaitingList")
	  public @ResponseBody String updateEventWaitingList (@RequestBody EventWaitingList eventWaitingList) {
		
		if(eventWaitingListService.addEventWaitingList(eventWaitingList)) {
			return "Updated eventWaitingList";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@GetMapping(path="/eventWaitingList/{id}")
	  public @ResponseBody EventWaitingList getEventWaitingListById(@PathVariable("id") Long id) {
	    return eventWaitingListService.getEventWaitingListById(id);
	    }
	
	@GetMapping(path="/eventWaitingLists")
	  public @ResponseBody List<EventWaitingList> getAllEventWaitingLists() {
	    return eventWaitingListService.getEventWaitingLists();
	  }

	@GetMapping(path="/eventWaitingList/{eventId}/firstInQueue")
	  public @ResponseBody Student getFirstStudentInQueue(@PathVariable("eventId") Long eventId) {
	    return eventWaitingListService.getFirstStudentInQueue(eventId);
	  }

		
}
