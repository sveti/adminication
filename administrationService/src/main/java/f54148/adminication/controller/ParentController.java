package f54148.adminication.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.StudentOfParentDTO;
import f54148.adminication.service.ParentService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/parents")
public class ParentController {
	
	private final ParentService parentService;
	
	@GetMapping(path = "/{id}/students")
	public @ResponseBody List<StudentOfParentDTO> getStudentOfParentDTO(@PathVariable("id") Long id) {
		return parentService.getStudentOfParentDTO(id);
	}

}
