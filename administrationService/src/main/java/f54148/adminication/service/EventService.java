package f54148.adminication.service;

import f54148.adminication.dto.*;

import java.util.List;

public interface EventService {
    String add(AddEventDTO event);

    String editEvent(EditEventDTO event);

    EditEventDTO getEditEventDTO(Long idEvent);

    EventReportDTO getEventReport(Long idEvent);

    List<EventTitlesDTO> getAllEventTitles();
}
