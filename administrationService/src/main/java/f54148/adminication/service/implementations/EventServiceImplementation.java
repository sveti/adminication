package f54148.adminication.service.implementations;

import f54148.adminication.dto.*;
import f54148.adminication.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Validated
public class EventServiceImplementation  implements EventService {
    private final RestTemplate restTemplate;
    @Override
    public String add(AddEventDTO event) {
        ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/events/add", event, String.class);
        return response.getBody();
    }

    @Override
    public String editEvent(EditEventDTO event) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EditEventDTO> requestEntity = new HttpEntity<>(event, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://databaseService/events/edit", HttpMethod.PUT,requestEntity,String.class);
        return response.getBody();
    }

    @Override
    public EditEventDTO getEditEventDTO(Long idEvent) {
        return restTemplate.getForObject("http://databaseService/events/edit/{idEvent}", EditEventDTO.class, idEvent);

    }

    @Override
    public EventReportDTO getEventReport(Long idEvent) {
        return restTemplate.getForObject("http://databaseService/events/report/{idCourse}",EventReportDTO.class, idEvent);

    }

    @Override
    public List<EventTitlesDTO> getAllEventTitles() {
        EventTitlesDTO[] events = restTemplate.getForObject("http://databaseService/events/titles/all", EventTitlesDTO[].class);
        assert events != null;
        return Arrays.asList(events);
    }
}
