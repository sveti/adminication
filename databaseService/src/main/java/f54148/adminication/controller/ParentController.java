package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Parent;
import f54148.adminication.entity.Role;
import f54148.adminication.entity.User;
import f54148.adminication.service.ParentService;
import f54148.adminication.service.UserService;


@Controller
public class ParentController {
	
	@Autowired 
	private UserService userService;
	
	@Autowired 
	private ParentService parentService;
	
	@PostMapping(path="/addP") 
	  public @ResponseBody String addNewParent (@RequestParam String name
	      , @RequestParam String lastName,@RequestParam Integer userId) {

		
		Parent p = new Parent();
		p.setName(name);
		p.setLastName(lastName);
		
		User u = userService.getUserById(userId);
		u.setRole(Role.ROLE_PARENT);
		userService.updateUser(u);
		p.setUser(u);
	
		if(parentService.addParent(p)) {
			return "Saved parent";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@PostMapping(path="/addChild") 
	  public @ResponseBody String addChild (@RequestParam Integer parentId
	      , @RequestParam Integer childId) {

		if(parentService.addStudent(parentId, childId)) {
			return "Added Child";
		}
		else {
			return "An error has occured";
		}
		

	  }
	
	
	@GetMapping(path="/parent")
	  public @ResponseBody Parent getParentById(@RequestParam Integer parentId) {
		System.out.println(parentService.getParentById(parentId));
	    return parentService.getParentById(parentId);
	    }
	
	@GetMapping(path="/parents")
	  public @ResponseBody List<Parent> getAllParents() {
	    return parentService.getParents();
	  }

}
