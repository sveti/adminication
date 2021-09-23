package f54148.adminication.controller;

import f54148.adminication.dto.*;
import f54148.adminication.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewCourse(@RequestBody AddEventDTO event) {
        return eventService.add(event);
    }

    @GetMapping(path = "/edit/{idEvent}")
    public @ResponseBody
    EditEventDTO getEditEventDTO(@PathVariable("idEvent") Long idEvent) {
        return eventService.getEditEventDTO(idEvent);
    }

    @PutMapping(path = "/edit")
    public @ResponseBody String editEvent(@RequestBody EditEventDTO event) {

        return eventService.editEvent(event);
    }

    @GetMapping(path = "/report/{idEvent}")
    public @ResponseBody
    EventReportDTO getEventReport(@PathVariable("idEvent") Long idEvent) {
        return eventService.getEventReport(idEvent);
    }

    @GetMapping(path = "/titles/all")
    public @ResponseBody
    List<EventTitlesDTO> getAllEventTitles() {
        return eventService.getAllEventTitles();
    }

}
