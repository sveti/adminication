package f54148.adminication.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.StudentScheduleDTO;
import f54148.adminication.service.ScheduleService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

	private final ScheduleService scheduleService;
	
	@GetMapping(path = "/student/{studentId}")
	public @ResponseBody List<StudentScheduleDTO> getStudentSchedule(@PathVariable("studentId") @Min(1) Long studentId) {
		return scheduleService.getStudentSchedule(studentId);
	}
	
}
