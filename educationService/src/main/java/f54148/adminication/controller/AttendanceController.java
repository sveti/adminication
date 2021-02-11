package f54148.adminication.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.AttendanceDTO;
import f54148.adminication.service.AttendanceService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {
	
	public AttendanceService attendanceService;
	
	@PutMapping(path = "/update")
	public @ResponseBody String updateAttendances(@RequestBody List<AttendanceDTO> attendances) {
		return attendanceService.updateAttendances(attendances);
	}

	
	@PostMapping(path = "/add")
	public @ResponseBody String addAttendances(@RequestBody List<AttendanceDTO> attendances) {
		return attendanceService.addAttendances(attendances);
	}
}
