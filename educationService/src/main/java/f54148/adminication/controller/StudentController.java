package f54148.adminication.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.GradesOfStudentDTO;
import f54148.adminication.service.StudentService;
import lombok.AllArgsConstructor;

import javax.annotation.security.RolesAllowed;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {
	
	private final StudentService studentService;
	
	@GetMapping(path = "/{id}/grades")
	@RolesAllowed({"PARENT","STUDENT"})
	public @ResponseBody List<GradesOfStudentDTO> getGradesOfStudent(@PathVariable("id") Long studentId) {
		return studentService.getGradesOfStudent(studentId);
	}

}
