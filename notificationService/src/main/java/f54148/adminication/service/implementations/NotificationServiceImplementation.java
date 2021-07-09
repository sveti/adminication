package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.NotificationDTO;
import f54148.adminication.service.NotificationService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class NotificationServiceImplementation implements NotificationService {

	 private final RestTemplate restTemplate;
	
	@Override
	public List<NotificationDTO> getAllNotificationOfUser(Long userid) {
		NotificationDTO notifications[] = restTemplate.getForObject("http://databaseService/users/notifications/user/{userid}",NotificationDTO[].class,userid);
		return Arrays.asList(notifications);
	}

	@Override
	public String dismissNotification(Long id) {
		String exists = restTemplate.getForObject("http://databaseService/users/notifications/{id}/dismiss",String.class, id);
		return exists;
	}

}
