package f54148.adminication.service;

import f54148.adminication.dto.AddEventDTO;
import f54148.adminication.dto.EditEventDTO;

public interface EventService {
    String add(AddEventDTO event);

    String editEvent(EditEventDTO event);

    EditEventDTO getEditEventDTO(Long idEvent);
}
