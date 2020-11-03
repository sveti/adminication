package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Draft;
import f54148.adminication.entity.Notification;
import f54148.adminication.entity.User;
import f54148.adminication.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private DraftService draftService;
	
	@Autowired
	private UserService userService;

	public List<Notification> getNotifications() {
		List<Notification> notificationList = new ArrayList<>();
		notificationRepository.findAll().forEach(notificationList::add);
		return notificationList;
	}

	public Notification getNotificationById(Long notificationId) {
		Optional<Notification> opNotification = notificationRepository.findById(notificationId);
		if (opNotification.isPresent()) {
			return opNotification.get();
		} else {
			return null;
		}
	}

	public boolean addNotification(Notification notification) {
		if (notificationRepository.save(notification) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateNotification(Notification notification) {
		if (notificationRepository.save(notification) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteNotification(Long notificationId) {
		Optional<Notification> opNotification = notificationRepository.findById(notificationId);
		if (opNotification.isPresent()) {
			Notification n = opNotification.get();
			
			Draft d = n.getDraft();
			d.getNotificationsSend().remove(n);
			draftService.updateDraft(d);
			
			User u = n.getRecipient();
			u.getNotificationsReceived().remove(n);
			userService.updateUser(u);
			
			notificationRepository.deleteById(notificationId);
			return true;
		} else {
			return false;
		}
	}





}
