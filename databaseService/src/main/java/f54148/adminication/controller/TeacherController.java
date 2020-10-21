package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Role;
import f54148.adminication.entity.Teacher;
import f54148.adminication.entity.User;
import f54148.adminication.service.TeacherService;
import f54148.adminication.service.UserService;

@Controller
public class TeacherController {

	@Autowired 
	private UserService userService;
	
	@Autowired 
	private TeacherService teacherService;
	
	@PostMapping(path="/addT") // Map ONLY POST Requests
	  public @ResponseBody String addNewTeacher (@RequestParam String name
	      , @RequestParam String lastName,@RequestParam Integer userId) {

		
		Teacher t = new Teacher();
		t.setName(name);
		t.setLastName(lastName);
		
		User u = userService.getUserById(userId);
		u.setRole(Role.ROLE_TEACHER);
		userService.updateUser(u);
		t.setUser(u);
	
		if(teacherService.addTeacher(t)) {
			return "Saved teacher";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@GetMapping(path="/teacher")
	  public @ResponseBody Teacher getTeacherById(@RequestParam Integer teacherId) {
		System.out.println(teacherService.getTeacherById(teacherId));
	    return teacherService.getTeacherById(teacherId);
	    }
	
	@GetMapping(path="/teachers")
	  public @ResponseBody List<Teacher> getAllTeachers() {
	    return teacherService.getTeachers();
	  }
	
}
