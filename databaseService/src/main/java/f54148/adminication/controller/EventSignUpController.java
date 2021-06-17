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

import f54148.adminication.dto.AddEventSignUpDTO;
import f54148.adminication.entity.EventSignUp;
import f54148.adminication.service.EventSignUpService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/events/eventSignUps")
public class EventSignUpController {

	private final EventSignUpService eventSignUpservice;

	@PostMapping(path = "/addEventSignUp")
	public @ResponseBody String addEventSignUp(@RequestBody EventSignUp eventSignUp) {

		if (eventSignUpservice.addEventSignUp(eventSignUp)) {
			return "Added eventSignUp";
		} else {
			return "An error has occured";
		}

	}
	
	@PostMapping(path = "/add")
	public @ResponseBody String addEventSignUpDTO(@RequestBody AddEventSignUpDTO enrollment) {
		if (eventSignUpservice.addEventSignUpDTO(enrollment)) {
			return "Student was sucessfully signed up for the event!";
		} else {
			return "An error has occured";
		}
	}


	@PutMapping(path = "/updateEventSignUp")
	public @ResponseBody String updateEventSignUp(@RequestBody EventSignUp eventSignUp) {

		if (eventSignUpservice.updateEventSignUp(eventSignUp)) {
			return "Updated eventSignUp";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/eventSignUp/{id}")
	public @ResponseBody EventSignUp getEventSignUpById(@PathVariable("id") Long id) {
		return eventSignUpservice.getEventSignUpById(id);
	}
	
	@DeleteMapping(path = "/eventSignUp/{id}")
	public @ResponseBody String deleteEventSignUpById(@PathVariable("id") Long id) {
		if (eventSignUpservice.deleteEventSignUp(id)) {
			return "Deleted eventSignUp!";
		} else {
			return "An error has occured";
		}
	}

	@GetMapping(path = "/eventSignUps")
	public @ResponseBody List<EventSignUp> getAllEventSignUps() {
		return eventSignUpservice.getEventSignUps();
	}
}
