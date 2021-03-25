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

import f54148.adminication.entity.Teaching;
import f54148.adminication.service.TeachingService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/teachers/teachings")
public class TeachingController {

	private final TeachingService teachingservice;

	@PostMapping(path = "/addTeaching")
	public @ResponseBody String addTeaching(@RequestBody Teaching teaching) {

		if (teachingservice.addTeaching(teaching)) {
			return "Added teaching";
		} else {
			return "An error has occured";
		}

	}

	@PutMapping(path = "/updateTeaching")
	public @ResponseBody String updateTeaching(@RequestBody Teaching teaching) {

		if (teachingservice.updateTeaching(teaching)) {
			return "Updated teaching";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/teaching/{id}")
	public @ResponseBody Teaching getTeachingById(@PathVariable("id") Long id) {
		return teachingservice.getTeachingById(id);
	}

	@DeleteMapping(path = "/teaching/{id}")
	public @ResponseBody String deleteTeachingById(@PathVariable("id") Long id) {
		if (teachingservice.deleteTeaching(id)) {
			return "Deleted teaching!";
		} else {
			return "An error has occured";
		}
	}

	@GetMapping(path = "/teachings")
	public @ResponseBody List<Teaching> getAllTeachings() {
		return teachingservice.getTeachings();
	}

	
	@GetMapping(path = "/{teacherId}")
	public @ResponseBody List<Teaching> getTeachingsByTeacherId(@PathVariable("teacherId") Long teacherId) {
		return teachingservice.getTeachingsByTeacherId(teacherId);
	}

	
}
