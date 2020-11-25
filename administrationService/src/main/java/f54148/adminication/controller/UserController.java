package f54148.adminication.controller;

import javax.validation.constraints.Min;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.dto.UserDTO;
import f54148.adminication.service.UserService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	
	@GetMapping(path = "/user/{id}")
	public @ResponseBody UserDTO getUser(@PathVariable("id") @Min(1) Long id) {
		return userService.getUser(id);
	}
}
