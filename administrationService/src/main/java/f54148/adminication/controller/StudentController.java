package f54148.adminication.controller;

import java.util.List;

import f54148.adminication.dto.DisplayParentDTO;
import f54148.adminication.dto.DisplayStudentDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.StudentMonthlyAttendanceDTO;
import f54148.adminication.service.StudentService;
import lombok.AllArgsConstructor;

import javax.annotation.security.RolesAllowed;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {
	
	StudentService studentService;

	@GetMapping(path = "/{studentId}/{month}/{year}")
	@RolesAllowed({"ADMIN","PARENT","STUDENT"})
	public @ResponseBody List<StudentMonthlyAttendanceDTO> getTeacherStatistics(@PathVariable("studentId") Long studentId, @PathVariable("month") Integer month,@PathVariable("year") Integer year) {
		return studentService.getStudentReport(studentId,month,year);
	}

	@GetMapping(path = "/admin/students")
	@RolesAllowed({"ADMIN"})
	public @ResponseBody List<DisplayStudentDTO> getAllStudentsAdmin() {
		return studentService.getAllStudentsAdmin();
	}
}
