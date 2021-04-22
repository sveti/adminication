package f54148.adminication.controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.Schedule;
import f54148.adminication.service.ScheduleService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/schedules")
public class SheduleController {

	private final ScheduleService scheduleService;
	
	@PostMapping(path = "/addSchedule")
	public @ResponseBody String addNewSchedule(@RequestBody Schedule schedule) {

		if (scheduleService.addSchedule(schedule)) {
			return "Saved schedule";
		} else {
			return "An error has occured";
		}

	}

	@PutMapping(path = "/updateSchedule")
	public @ResponseBody String updateSchedule(@RequestBody Schedule schedule) {

		if (scheduleService.addSchedule(schedule)) {
			return "Updated schedule";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/schedule/{id}")
	public @ResponseBody Schedule getScheduleById(@PathVariable("id") Long id) {
		return scheduleService.getScheduleById(id);
	}

	@DeleteMapping(path = "/schedule/{id}")
	public @ResponseBody String deleteScheduleById(@PathVariable("id") Long id) {
		if (scheduleService.deleteSchedule(id)) {
			return "Deleted Schedule!";
		} else {
			return "An error has occured!";
		}
	}

	@GetMapping(path = "/schedule/{id}/courses")
	public @ResponseBody Set<Course> getCoursesbyScheduleId(@PathVariable("id") Long id) {
		return scheduleService.getCoursesbyScheduleId(id);
	}

	@GetMapping(path = "/schedule/{id}/events")
	public @ResponseBody Set<Event> getEventsbyScheduleId(@PathVariable("id") Long id) {
		return scheduleService.getEventsbyScheduleId(id);
	}

	@GetMapping(path = "/schedules")
	public @ResponseBody List<Schedule> getAllSchedules() {
		return scheduleService.getSchedules();
	}
}
