package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Parent;
import f54148.adminication.service.ParentService;


@Controller
public class ParentController {
	
	
	@Autowired 
	private ParentService parentService;
	
	@PostMapping(path="/addP") 
	  public @ResponseBody String addNewParent (@RequestBody Parent parent) {
		
		if(parentService.addParent(parent)) {
			return "Saved parent";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@PutMapping(path="/updateP")
	 public @ResponseBody String updateParent (@RequestBody Parent parent) {
		
		if(parentService.updateParent(parent)) {
			return "Child adopted!";
		}
		else {
			return "An error has occured";
		}
		
	}
	
	
	
	@GetMapping(path="/parent/{id}")
	  public @ResponseBody Parent getParentById(@PathVariable("id") Long id) {
	    return parentService.getParentById(id);
	    }
	
	@GetMapping(path="/parents")
	  public @ResponseBody List<Parent> getAllParents() {
	    return parentService.getParents();
	  }

}
