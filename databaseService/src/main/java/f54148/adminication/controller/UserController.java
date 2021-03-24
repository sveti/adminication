package f54148.adminication.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.dto.DisplayUserDTO;
import f54148.adminication.dto.EditUserDTO;
import f54148.adminication.entity.Draft;
import f54148.adminication.entity.Notification;
import f54148.adminication.entity.Role;
import f54148.adminication.entity.User;
import f54148.adminication.service.UserService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@PostMapping(path = "/addUser")
	public @ResponseBody String addUser(@RequestBody User user) {

		if (userService.addUser(user)) {
			return "Saved user";
		} else {
			return "A problem has occured";
		}
	}
	
	@PutMapping(path = "/updateUser")
		public @ResponseBody String updateUser(@RequestBody EditUserDTO user) {

			if (userService.updateUser(user)) {
				return "true";
			} else {
				return "false";
			}
		}
	
	@GetMapping(path = "/users")
	public @ResponseBody List<User> getAllUsers() {
		return userService.getUsers();
	}

	@GetMapping(path = "/user/{id}")
	public @ResponseBody User getUser(@PathVariable("id") @Min(1) Long id) {
		return userService.getUserById(id);
	}
	
	@GetMapping(path = "/displayUserDTO/{username}")
	public @ResponseBody DisplayUserDTO getCreateUserDTO(@PathVariable("username") @Min(1) String username) {
		return userService.getCreateUserDTOByUsername(username);
	}
	
	
	@GetMapping(path = "/{username}")
	public @ResponseBody User getUserByUsername(@PathVariable("username") String username) {
		return userService.getUserByUsername(username);
	}
	
	@GetMapping(path = "/user/{id}/role")
	public @ResponseBody Role getUserRole(@PathVariable("id") Long id) {
		return userService.getUserRole(id);
	}
	
	@GetMapping(path = "/user/{id}/roleName")
	public @ResponseBody String getUserRoleName(@PathVariable("id") Long id) {
		return userService.getUserRole(id).getName();
	}
	
	@GetMapping(path = "/user/{id}/notificationsReceived")
	public @ResponseBody List<Notification> getNotificationsReceivedByUser(@PathVariable("id") Long id) {
		return userService.getNotificationsReceivedByUser(id);
	}
	
	@GetMapping(path = "/user/{id}/notificationsSend")
	public @ResponseBody List<Notification> getNotificationsSendByUser(@PathVariable("id") Long id) {
		return userService.getNotificationsSendByUser(id);
	}
	
	@GetMapping(path = "/user/{id}/drafts")
	public @ResponseBody List<Draft> getDraftsByUser(@PathVariable("id") Long id) {
		return userService.getDraftsByUser(id);
	}
	
	@GetMapping(path = "/validateEmail/{email}")
	public @ResponseBody String validateEmail(@PathVariable("email") String email) {

		if (userService.emailExists(email)) {
			return "true";
		} else {
			return "false";
		}
	}
	
	@GetMapping(path = "/validateUsername/{username}")
	public @ResponseBody String validateUsername(@PathVariable("username") String username) {

		if (userService.usernameExists(username)) {
			return "true";
		} else {
			return "false";
		}
	}

}
