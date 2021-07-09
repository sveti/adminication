package f54148.adminication.service;

import java.util.List;

import f54148.adminication.dto.NotificationDTO;

public interface NotificationService {

	List<NotificationDTO> getAllNotificationOfUser(Long id);

	String dismissNotification(Long id);

}
