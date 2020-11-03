package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Draft;
import f54148.adminication.entity.Notification;
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

	@PutMapping(path = "/updateUser")
		public @ResponseBody String updateUser(@RequestBody User user) {

			if (userService.addUser(user)) {
				return "Updated user";
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

}
