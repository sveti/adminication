package f54148.adminication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Event;
import f54148.adminication.entity.Schedule;
import f54148.adminication.service.EventService;
import f54148.adminication.service.ScheduleService;

@Controller
public class EventController {
	
	
	@Autowired 
	private EventService eventService;
	
	@Autowired 
	private ScheduleService scheduleService;

	@PostMapping(path="/addEvent")
	  public @ResponseBody String addNewEvent (@RequestBody Event event) {
		
		List<Schedule>schedules = new ArrayList<Schedule>();
		
		for(Schedule s: event.getEventSchedule()) {
			
			if(s.getId()==null) {
				scheduleService.addSchedule(s);
			}		
			schedules.add(s);
		}
		
		event.setEventSchedule(null);
		
		if(eventService.addEvent(event)) {
			event.setEventSchedule(schedules);
			updateEvent(event);
			return "Saved event";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@PutMapping(path="/updateEvent")
	  public @ResponseBody String updateEvent (@RequestBody Event event) {
		
		if(eventService.addEvent(event)) {
			return "Updated event";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@GetMapping(path="/event/{id}")
	  public @ResponseBody Event getEventById(@PathVariable("id") Long id) {
	    return eventService.getEventById(id);
	    }
	
	@GetMapping(path="/events")
	  public @ResponseBody List<Event> getAllEvents() {
	    return eventService.getEvents();
	  }

}
