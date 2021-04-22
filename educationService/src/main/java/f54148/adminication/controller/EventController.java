package f54148.adminication.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.EventDTO;
import f54148.adminication.service.EventService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {
	
	private final EventService eventService;
	
	@GetMapping(path = "/events")
	public @ResponseBody List<EventDTO> getEventsDTO() {
		return eventService.getEvents();
	}

	@GetMapping(path = "/student/{studentId}")
	public @ResponseBody List<EventDTO> getEventsOfStudent(@PathVariable("studentId") Long studentId) {
		return eventService.getEventsOfStudent(studentId);
	}
}
