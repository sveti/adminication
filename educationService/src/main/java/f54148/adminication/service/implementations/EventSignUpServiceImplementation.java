package f54148.adminication.service.implementations;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.AddEventSignUpDTO;
import f54148.adminication.service.EventSignUpService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class EventSignUpServiceImplementation implements EventSignUpService {
	
	private final RestTemplate restTemplate;

	@Override
	public String addAddEventSignUpDTO(AddEventSignUpDTO dto) {
		ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/events/eventSignUps/add", dto, String.class);
		return response.getBody();
	}

}
