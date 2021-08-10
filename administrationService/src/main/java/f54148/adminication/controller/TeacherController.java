package f54148.adminication.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.AddTeacherDTO;
import f54148.adminication.dto.DisplayTeacherDTO;
import f54148.adminication.dto.MonthlyTeacherSalaryDTO;
import f54148.adminication.dto.TeacherForCourseDTO;
import f54148.adminication.service.TeacherService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
	
private final TeacherService teacherService;
	
@GetMapping(path = "/{teacherId}/{month}/{year}")
public @ResponseBody MonthlyTeacherSalaryDTO getTeacherStatistics(@PathVariable("teacherId") Long teacherId, @PathVariable("month") Integer month,@PathVariable("year") Integer year) {
	return teacherService.getTeacherStatistics(teacherId,month,year);
}

@GetMapping(path = "/teachersForCourse")
public @ResponseBody List<TeacherForCourseDTO> getAllTeacherForCourseDTO() {
	return teacherService.getAllTeacherForCourseDTO();
}

@GetMapping(path = "/all")
public @ResponseBody List<DisplayTeacherDTO> getAllDisplayTeacherDTO() {
	return teacherService.getAllDisplayTeacherDTO();
}

@PostMapping(path = "/add")
public @ResponseBody String adminAddTeacher(@RequestBody AddTeacherDTO teacher) {

	return teacherService.adminAddTeacher(teacher);

}

}
