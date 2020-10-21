package f54148.adminication.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Role;
import f54148.adminication.entity.User;

import f54148.adminication.service.UserService;

@Controller
public class UserController {
	
	@Autowired 
	private UserService userService;
	
	@PostMapping(path="/add") // Map ONLY POST Requests
	  public @ResponseBody String addNewUser (@RequestParam String username
	      , @RequestParam String email,@RequestParam String password) {

	    User n = new User();
	    n.setUsername(username);
	    n.setEmail(email);
	    n.setPassword(password);
	    n.setRole(Role.ROLE_STUDENT);
	    if(userService.addUser(n)) {
	    	return "Saved user";
	    }
	    else {
	    	return "A problem has occured";
	    }
	  }

	@GetMapping(path="/users")
	  public @ResponseBody List<User> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return userService.getUsers();
	  }

}
