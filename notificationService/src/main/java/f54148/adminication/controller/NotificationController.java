package f54148.adminication.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.NotificationDTO;
import f54148.adminication.service.NotificationService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

	private final NotificationService notificationService;
	
	@GetMapping(path = "/user/{id}")
	public @ResponseBody List<NotificationDTO> getAllNotificationOfUser(@PathVariable("id") Long id) {
		return notificationService.getAllNotificationOfUser(id);
	}
	
	@GetMapping(path = "/{id}/dismiss")
	public @ResponseBody String dismissNotification(@PathVariable("id") Long id) {
		return notificationService.dismissNotification(id);
	}
	
}
