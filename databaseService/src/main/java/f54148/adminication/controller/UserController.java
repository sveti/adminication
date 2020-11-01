package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.User;

import f54148.adminication.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/addUser")
	public @ResponseBody String addUser(@RequestBody User user) {

		if (userService.addUser(user)) {
			return "Saved user";
		} else {
			return "A problem has occured";
		}
	}

	@GetMapping(path = "/users")
	public @ResponseBody List<User> getAllUsers() {
		return userService.getUsers();
	}

	@GetMapping(path = "/user/{id}")
	public @ResponseBody User getUser(@PathVariable("id") Long id) {
		return userService.getUserById(id);
	}

}
