package f54148.adminication.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.DisplayUserDTO;
import f54148.adminication.service.TeacherService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
	
private final TeacherService teacherService;
	


}
