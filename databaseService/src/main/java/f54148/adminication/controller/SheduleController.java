package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Schedule;
import f54148.adminication.service.ScheduleService;

@Controller
public class SheduleController {
	
	@Autowired 
	private ScheduleService scheduleService;

	@PostMapping(path="/addShedule")
	  public @ResponseBody String addNewSchedule (@RequestBody Schedule schedule) {
		
		if(scheduleService.addSchedule(schedule)) {
			return "Saved schedule";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@PutMapping(path="/updateShedule")
	  public @ResponseBody String updateSchedule (@RequestBody Schedule schedule) {
		
		if(scheduleService.addSchedule(schedule)) {
			return "Updated schedule";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@GetMapping(path="/schedule/{id}")
	  public @ResponseBody Schedule getScheduleById(@PathVariable("id") Long id) {
	    return scheduleService.getScheduleById(id);
	    }
	
	@GetMapping(path="/schedules")
	  public @ResponseBody List<Schedule> getAllSchedules() {
	    return scheduleService.getSchedules();
	  }
}
