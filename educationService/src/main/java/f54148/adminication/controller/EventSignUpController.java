package f54148.adminication.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.AddEventSignUpDTO;
import f54148.adminication.service.EventSignUpService;
import lombok.AllArgsConstructor;

import javax.annotation.security.RolesAllowed;

@RestController
@AllArgsConstructor
@RequestMapping("/eventSignUps")
public class EventSignUpController {

	private final EventSignUpService eventSignUpService;
	@PostMapping(path = "/add")
	@RolesAllowed({"PARENT"})
	public @ResponseBody String addEventSignUpDTO(@RequestBody AddEventSignUpDTO dto) {
		return eventSignUpService.addAddEventSignUpDTO(dto);
	}
	
	@DeleteMapping(path = "/delete/{studentId}/{eventId}")
	@RolesAllowed({"PARENT"})
	public @ResponseBody String deleteEnrollmentByStudentAndCourse(@PathVariable("studentId") Long studentId,@PathVariable("eventId") Long eventId) {
		return eventSignUpService.deleteEventSignUpByStudentAndCourse(studentId,eventId);
	}
}
