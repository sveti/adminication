package f54148.adminication.controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.dto.GradesOfStudentDTO;
import f54148.adminication.dto.MonthlyTeacherSalaryDTO;
import f54148.adminication.dto.StudentMonthlyAttendanceDTO;
import f54148.adminication.dto.StudentScheduleDTO;
import f54148.adminication.entity.Attendance;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.Parent;
import f54148.adminication.entity.Student;
import f54148.adminication.service.ParentService;
import f54148.adminication.service.StudentService;
import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

	private final StudentService studentService;

	private final ParentService parentService;


	@PostMapping(path = "/addStudent")
	public @ResponseBody String addNewStudent(@RequestBody Student student) {

		if (student.getParent() != null && parentService.getParentById(student.getParent().getId()) == null) {

			parentService.addParent(student.getParent());

		}

		if (studentService.addStudent(student)) {
			return "Saved student";
		} else {
			return "An error has occured";
		}

	}

	@PutMapping(path = "/updateStudent")
	public @ResponseBody String updateStudent(@RequestBody Student student) {

		if (studentService.updateStudent(student)) {
			return "Updated student";
		} else {
			return "An error has occured";
		}

	}
	
	@GetMapping(path = "/student/{id}")
	public @ResponseBody Student getStudentById(@PathVariable("id") Long studentId) {
		return studentService.getStudentById(studentId);
	}

	@GetMapping(path = "/students")
	public @ResponseBody List<Student> getAllStudents() {
		return studentService.getStudents();
	}

	@GetMapping(path = "student/{id}/parent")
	public @ResponseBody Parent getStudentParent(@PathVariable("id") Long studentId) {

		return studentService.getStudentById(studentId).getParent();
	}

	@GetMapping(path = "student/{id}/courses")
	public @ResponseBody List<Course> getStudentCourses(@PathVariable("id") Long studentId) {
		return studentService.getCoursesStudentById(studentId);
	}

	@GetMapping(path = "student/{id}/events")
	public @ResponseBody List<Event> getStudentEvents(@PathVariable("id") Long studentId) {
		return studentService.getEventsStudentById(studentId);
	}
	
	@GetMapping(path = "student/{id}/attendances")
	public @ResponseBody Set<Attendance> getStudentAttendances(@PathVariable("id") Long studentId) {
		return studentService.getStudentAttendances(studentId);
	}

	@GetMapping(path = "student/{id}/events/waiting")
	public @ResponseBody List<Event> getStudentWaitingEvents(@PathVariable("id") Long studentId) {
		return studentService.getStudentWaitingEvents(studentId);
	}

	@GetMapping(path = "student/{id}/courses/waiting")
	public @ResponseBody List<Course> getStudentWaitingCourses(@PathVariable("id") Long studentId) {
		return studentService.getStudentWaitingCourses(studentId);
	}
	
	@GetMapping(path = "/{id}/schedule")
	public @ResponseBody List<StudentScheduleDTO> getStudentSchedule(@PathVariable("id") Long studentId) {
		return studentService.getStudentSchedule(studentId);
	}

	
	@GetMapping(path = "/{id}/grades")
	public @ResponseBody List<GradesOfStudentDTO> getGradesOfStudent(@PathVariable("id") Long studentId) {
		return studentService.getGradesOfStudent(studentId);
	}

	@GetMapping(path = "/{studentId}/{month}/{year}")
	public @ResponseBody List<StudentMonthlyAttendanceDTO> getTeacherStatistics(@PathVariable("studentId") Long studentId, @PathVariable("month") Integer month,@PathVariable("year") Integer year) {
		return studentService.getStudentReport(studentId,month,year);
	}
	
}
