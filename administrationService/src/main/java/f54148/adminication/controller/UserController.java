package f54148.adminication.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.CreateUserDTO;
import f54148.adminication.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	
	@GetMapping(path = "/")
	public @ResponseBody List<CreateUserDTO> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping(path = "/{id}")
	public @ResponseBody CreateUserDTO getUser(@PathVariable("id") @Min(1) Long id) {
		return userService.getUser(id);
	}
	
	@PostMapping(path = "/addUser")
	public @ResponseBody String createUser(@RequestBody CreateUserDTO userDTO) {
		return userService.createUser(userDTO);
	}
	
}
