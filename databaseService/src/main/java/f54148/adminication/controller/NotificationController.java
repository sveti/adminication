package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Notification;
import f54148.adminication.service.NotificationService;

@Controller
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;


	@PostMapping(path = "/addNotification")
	public @ResponseBody String addNotification(@RequestBody Notification notification) {
		

		if (notificationService.addNotification(notification)) {
			return "Saved notification";
		} else {
			return "A problem has occured";
		}
	}
	
	@PutMapping(path = "/updateNotification")
	public @ResponseBody String updateNotification(@RequestBody Notification notification) {
		

		if (notificationService.addNotification(notification)) {
			return "Updated notification";
		} else {
			return "A problem has occured";
		}
	}

	@GetMapping(path = "/notifications")
	public @ResponseBody List<Notification> getAllNotifications() {
		return notificationService.getNotifications();
	}

	@GetMapping(path = "/notification/{id}")
	public @ResponseBody Notification getNotification(@PathVariable("id") Long id) {
		return notificationService.getNotificationById(id);
	}
	
	@DeleteMapping(path = "/notification/{id}")
	public @ResponseBody String deleteNotification(@PathVariable("id") Long id) {
		if( notificationService.deleteNotification(id)) {
			return "Notification deleted!";
		}
		else {
			return "An error has occured";
		}
	}

}
