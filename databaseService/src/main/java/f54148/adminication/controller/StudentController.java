package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.Parent;
import f54148.adminication.entity.Student;
import f54148.adminication.service.StudentService;


@Controller
public class StudentController {

	@Autowired 
	private StudentService studentService;

	
	@PostMapping(path="/addS") 
	  public @ResponseBody String addNewStudent (@RequestBody Student student) {
		
		if(studentService.addStudent(student)) {
			return "Saved student";
		}
		else {
			return "An error has occured";
		}
	    
	  }
	
	@GetMapping(path="student/{id}/parent")
	 public @ResponseBody Parent getStudentParent(@PathVariable("id") Long studentId) {
		
	    return studentService.getStudentById(studentId).getParent();
	    }
	
	@GetMapping(path="student/{id}/courses")
	 public @ResponseBody List<Course> getStudentCourses(@PathVariable("id") Long studentId) {
	    return studentService.getCoursesStudentById(studentId);
	    }
	
	@GetMapping(path="/student/{id}")
	  public @ResponseBody Student getStudentById(@PathVariable("id") Long studentId) {
	    return studentService.getStudentById(studentId);
	    }
	
	@GetMapping(path="/students")
	  public @ResponseBody List<Student> getAllStudents() {
	    return studentService.getStudents();
	  }

}
