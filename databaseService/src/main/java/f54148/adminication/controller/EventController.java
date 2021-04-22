package f54148.adminication.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.dto.EventDTO;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.Schedule;
import f54148.adminication.entity.Student;
import f54148.adminication.service.EventService;
import f54148.adminication.service.ScheduleService;

@Controller
@RequestMapping("/events")
public class EventController {

	private final EventService eventService;

	private final ScheduleService scheduleService;
	
	public EventController(EventService eventService,@Lazy ScheduleService scheduleService) {
		super();
		this.eventService = eventService;
		this.scheduleService = scheduleService;
	}

	@PostMapping(path = "/addEvent")
	public @ResponseBody String addNewEvent(@RequestBody Event event) {

		Set<Schedule> schedules = new HashSet<Schedule>();

		for (Schedule s : event.getEventSchedule()) {

			if (s.getId() == null) {
				scheduleService.addSchedule(s);
			}
			schedules.add(s);
		}

		event.setEventSchedule(null);

		if (eventService.addEvent(event)) {
			event.setEventSchedule(schedules);
			updateEvent(event);
			return "Saved event";
		} else {
			return "An error has occured";
		}

	}

	@PutMapping(path = "/updateEvent")
	public @ResponseBody String updateEvent(@RequestBody Event event) {

		if (eventService.addEvent(event)) {
			return "Updated event";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/event/{id}")
	public @ResponseBody Event getEventById(@PathVariable("id") Long id) {
		return eventService.getEventById(id);
	}
	@GetMapping(path = "/event/{id}/schedule")
	public @ResponseBody Set<Schedule> getScheduleByEventId(@PathVariable("id") Long id) {
		return eventService.getScheduleByEventId(id);
	}
	
	@GetMapping(path = "/event/{id}/students")
	public @ResponseBody List<Student> getStudentsByEventId(@PathVariable("id") Long id) {
		return eventService.getStudentsByEventId(id);
	}

	@GetMapping(path = "/event/{id}/waiting")
	public @ResponseBody List<Student> getStudentsWaitingByEventId(@PathVariable("id") Long id) {
		return eventService.getStudentsWaitingByEventId(id);
	}

	@GetMapping(path = "/all")
	public @ResponseBody List<Event> getAllEvents() {
		return eventService.getEvents();
	}

	@GetMapping(path = "/events")
	public @ResponseBody List<EventDTO> getEvents() {
		return eventService.getAllEvents();
	}

	@GetMapping(path = "/student/{studentId}")
	public @ResponseBody List<EventDTO> getEventsOfStudent(@PathVariable("studentId") Long studentId) {
		return eventService.getEventsOfStudent(studentId);
	}

	
}
