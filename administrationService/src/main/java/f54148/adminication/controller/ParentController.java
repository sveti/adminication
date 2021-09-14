package f54148.adminication.controller;

import java.util.List;

import f54148.adminication.dto.AddParentDTO;
import f54148.adminication.dto.DisplayParentDTO;
import f54148.adminication.dto.EditUserDTO;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping(path = "/admin/parents")
	public @ResponseBody List<DisplayParentDTO> getAllParentsAdmin() {
		return parentService.getAllParentsAdmin();
	}

	@PostMapping (path = "/add")
	public  @ResponseBody String addParent(@RequestBody AddParentDTO addParentDTO){
		return parentService.addParent(addParentDTO);
	}
}
