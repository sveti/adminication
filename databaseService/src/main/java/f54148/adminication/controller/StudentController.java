package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Student;
import f54148.adminication.entity.User;
import f54148.adminication.service.ParentService;
import f54148.adminication.service.StudentService;
import f54148.adminication.service.UserService;


@Controller
public class StudentController {

	@Autowired
	private UserService userservice;
	
	@Autowired 
	private StudentService studentService;
	
	@Autowired
	private ParentService parentService;
	
	@PostMapping(path="/addS") 
	  public @ResponseBody String addNewStudent (@RequestBody Student student) {
		
		if(studentService.addStudent(student)) {
			return "Saved student";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@PostMapping(path="/addParent")
	 public @ResponseBody String addParentToChild (@RequestBody Student student) {
		
		if(studentService.addStudent(student)) {
			parentService.updateParent(student.getParent());
			return "Child adopted!";
		}
		else {
			return "An error has occured";
		}
		
	}
	
	@GetMapping(path="/student")
	  public @ResponseBody Student getStudentById(@RequestParam Long studentId) {
		System.out.println(studentService.getStudentById(studentId));
	    return studentService.getStudentById(studentId);
	    }
	
	@GetMapping(path="/students")
	  public @ResponseBody List<Student> getAllStudents() {
	    return studentService.getStudents();
	  }

}
