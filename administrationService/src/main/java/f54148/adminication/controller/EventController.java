package f54148.adminication.controller;

import f54148.adminication.dto.AddCourseDTO;
import f54148.adminication.dto.AddEventDTO;
import f54148.adminication.dto.DisplayEditCourseDTO;
import f54148.adminication.dto.EditEventDTO;
import f54148.adminication.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
