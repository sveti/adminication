package f54148.adminication.controller;

import java.util.List;
import java.util.Set;

import f54148.adminication.dto.AddParentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.dto.StudentOfParentDTO;
import f54148.adminication.entity.Parent;
import f54148.adminication.entity.Student;
import f54148.adminication.service.ParentService;

@Controller
@AllArgsConstructor
@RequestMapping("/parents")
public class ParentController {

	private final ParentService parentService;

	@PostMapping(path = "/addParent")
	public @ResponseBody String addNewParent(@RequestBody Parent parent) {

		if (parentService.addParent(parent)) {
			return "Saved parent";
		} else {
			return "An error has occured";
		}

	}

	@PutMapping(path = "/updateParent")
	public @ResponseBody String updateParent(@RequestBody Parent parent) {

		if (parentService.updateParent(parent)) {
			return "Updated parent!";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/parent/{id}")
	public @ResponseBody Parent getParentById(@PathVariable("id") Long id) {
		return parentService.getParentById(id);
	}

	@GetMapping(path = "/parent/{id}/children")
	public @ResponseBody Set<Student> getChildrenByParentId(@PathVariable("id") Long id) {
		return parentService.getChildrenByParentId(id);
	}

	@GetMapping(path = "/parents")
	public @ResponseBody List<Parent> getAllParents() {
		return parentService.getParents();
	}
	
	@GetMapping(path = "/{id}/students")
	public @ResponseBody List<StudentOfParentDTO> getStudentOfParentDTO(@PathVariable("id") Long id) {
		return parentService.getStudentOfParentDTO(id);
	}

	@PostMapping(path = "/add")
	public @ResponseBody String addAddParentDTO(@RequestBody AddParentDTO parent) {

		return parentService.addAddParentDTO(parent);

	}
}
