package f54148.adminication.service;

import java.util.List;

import f54148.adminication.dto.AdminAllEventsDTO;
import f54148.adminication.dto.EventDTO;

public interface EventService {

	List <EventDTO> getEvents();

	List<EventDTO> getEventsOfStudent(Long studentId);

    List<AdminAllEventsDTO> getAllEvents();
}
