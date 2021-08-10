package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.EventDTO;
import f54148.adminication.service.EventService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class EventServiceImplementation implements EventService{

	private final RestTemplate restTemplate;
	
	@Override
	public List<EventDTO> getEvents() {
		EventDTO[] events = restTemplate.getForObject("http://databaseService/events/events",EventDTO[].class);
		assert events != null;
		return Arrays.asList(events);
	}

	@Override
	public List<EventDTO> getEventsOfStudent(Long studentId) {
		EventDTO[] events = restTemplate.getForObject("http://databaseService/events/student/{studentId}",EventDTO[].class,studentId);
		assert events != null;
		return Arrays.asList(events);
	}

}
