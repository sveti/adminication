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
import f54148.adminication.entity.Student;
import f54148.adminication.entity.User;
import f54148.adminication.service.ParentService;
import f54148.adminication.service.StudentService;
import f54148.adminication.service.UserService;

@Controller
public class StudentController {

	
	@Autowired 
	private UserService userService;
	
	@Autowired 
	private StudentService studentService;
	
	@Autowired
	private ParentService parentService;
	
	@PostMapping(path="/addS") 
	  public @ResponseBody String addNewStudent (@RequestParam String name
	      , @RequestParam String lastName,@RequestParam Integer userId,
	       @RequestParam Integer parentId) {

		
		Student s = new Student();
		s.setName(name);
		s.setLastName(lastName);
		
		User u = userService.getUserById(userId);
		u.setRole(Role.ROLE_STUDENT);
		userService.updateUser(u);
		s.setUser(u);
		
		Parent p = parentService.getParentById(parentId);
		p.addChild(s);
		s.setParent(p);

		if(studentService.addStudent(s)) {
			parentService.updateParent(p);
			return "Saved student";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@GetMapping(path="/student")
	  public @ResponseBody Student getStudentById(@RequestParam Integer studentId) {
		System.out.println(studentService.getStudentById(studentId));
	    return studentService.getStudentById(studentId);
	    }
	
	@GetMapping(path="/students")
	  public @ResponseBody List<Student> getAllStudents() {
	    return studentService.getStudents();
	  }

}
