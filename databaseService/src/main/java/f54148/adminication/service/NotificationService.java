package f54148.adminication.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import f54148.adminication.dto.NotificationDTO;
import f54148.adminication.entity.Draft;
import f54148.adminication.entity.MessageStatus;
import f54148.adminication.entity.Notification;
import f54148.adminication.entity.User;
import f54148.adminication.repository.NotificationRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationService {

	private final NotificationRepository notificationRepository;

	private final DraftService draftService;

	private final UserService userService;
	
	private final ModelMapper modelMapper;
	

	public List<Notification> getNotifications() {
		List<Notification> notificationList = new ArrayList<>();
		notificationRepository.findAll().forEach(notificationList::add);
		return notificationList;
	}

	public Notification getNotificationById(Long notificationId) {
		Optional<Notification> opNotification = notificationRepository.findById(notificationId);
		return opNotification.orElse(null);
	}

	public boolean addNotification(Notification notification) {
		notificationRepository.save(notification);
		return true;
	}
	
	public Notification addNotificationAndGetNotification(Notification notification) {
		return notificationRepository.save(notification);
	}

	public boolean updateNotification(Notification notification) {
		notificationRepository.save(notification);
		return true;
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

	public List<Notification> getNotificationsSendByUserID(Long id) {
		List<Notification> notificationList = getNotifications();

		List<Notification> fromUser = new ArrayList<>();

		for (Notification n : notificationList) {

			if (n.getDraft().getSender().getId() == id) {
				fromUser.add(n);
			}

		}
		return fromUser;
	}
	
	public void sendDraft(Long draftId, Long userId) {
		
		try {
		Notification n = new Notification();
		Draft d = draftService.getDraftById(draftId);
		n.setDraft(d);
		n.setRecipient(userService.getUserById(userId));
		n.setSend(LocalDateTime.now());
		n.setStatus(MessageStatus.SEND);
		
		Notification saved = addNotificationAndGetNotification(n);
		draftService.addNotification(d,saved);

		}
		catch(Exception e) {
		}
		
		
	}

	public NotificationDTO convertToNotificationDTO(Notification n) {
		
		
		NotificationDTO dto = modelMapper.map(n, NotificationDTO.class);
		dto.setMessage(n.getDraft().getContent());
		dto.setSenderName(n.getDraft().getSender().getName() + " " +n.getDraft().getSender().getLastName());
		
		return dto;
	}

	public List<NotificationDTO> getAllNotificationsOfUserById(Long userId) {
		List<Notification>nots = userService.getUserById(userId).getNotificationsReceived();
		List<NotificationDTO> dtos = new ArrayList<>();
		for( Notification n: nots){
			dtos.add(convertToNotificationDTO(n));
		}
		return dtos;
	}

	public Boolean dismissNotificationById(Long notificationId) {
		try {
			
			Notification not = getNotificationById(notificationId);
			not.setStatus(MessageStatus.OPENED);
			return updateNotification(not);
		}
		catch(Exception e) {
			return false;
		}
	}


}
