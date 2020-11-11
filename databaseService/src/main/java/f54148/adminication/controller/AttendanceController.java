package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Attendance;
import f54148.adminication.service.AttendanceService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/courses/attendances")
public class AttendanceController {
	private final AttendanceService attendanceservice;

	@PostMapping(path = "/addAttendance")
	public @ResponseBody String addAttendance(@RequestBody Attendance attendance) {

		if (attendanceservice.addAttendance(attendance)) {
			return "Added attendance";
		} else {
			return "An error has occured";
		}

	}

	@PutMapping(path = "/updateAttendance")
	public @ResponseBody String updateAttendance(@RequestBody Attendance attendance) {

		if (attendanceservice.updateAttendance(attendance)) {
			return "Updated attendance";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/attendance/{id}")
	public @ResponseBody Attendance getAttendanceById(@PathVariable("id") Long id) {
		return attendanceservice.getAttendanceById(id);
	}

	@DeleteMapping(path = "/attendance/{id}")
	public @ResponseBody String deleteAttendanceById(@PathVariable("id") Long id) {
		if (attendanceservice.deleteAttendance(id)) {
			return "Deleted attendance!";
		} else {
			return "An error has occured";
		}
	}

	@GetMapping(path = "/attendances")
	public @ResponseBody List<Attendance> getAllAttendances() {
		return attendanceservice.getAttendances();
	}

}
