package f54148.adminication.controller;

import java.util.List;
import java.util.Set;

import f54148.adminication.exceptions.EmailTakenException;
import f54148.adminication.exceptions.UsernameTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.dto.AddTeacherDTO;
import f54148.adminication.dto.DisplayTeacherDTO;
import f54148.adminication.dto.MonthlyTeacherSalaryDTO;
import f54148.adminication.dto.TeacherForCourseDTO;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.File;
import f54148.adminication.entity.Lesson;
import f54148.adminication.entity.Teacher;
import f54148.adminication.service.TeacherService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

	
	private final TeacherService teacherService;

	@PostMapping(path = "/addTeacher")
	public @ResponseBody String addNewTeacher(@RequestBody Teacher teacher) {

		if (teacherService.addTeacher(teacher)) {
			return "Saved teacher";
		} else {
			return "An error has occured";
		}

	}
	
	@PutMapping(path = "/updateTeacher")
	public @ResponseBody String updateTeacher(@RequestBody Teacher teacher) {

		if (teacherService.updateTeacher(teacher)) {
			return "Updated teacher";
		} else {
			return "An error has occured";
		}
		
	}

	@GetMapping(path = "/teacher/{id}")
	public @ResponseBody Teacher getTeacherById(@PathVariable("id") Long id) {
		return teacherService.getTeacherById(id);
	}


	@GetMapping(path = "/teacher/{id}/courses")
	public @ResponseBody List<Course> getCoursesByTeacherId(@PathVariable("id") Long id) {
		return teacherService.getCoursesByTeacherId(id);
	}

	@GetMapping(path = "/teacher/{id}/lessons")
	public @ResponseBody Set<Lesson> getLessonsByTeacherId(@PathVariable("id") Long id) {
		return teacherService.getLessonsByTeacherId(id);
	}
	
	@GetMapping(path = "/teacher/{id}/files")
	public @ResponseBody Set<File> getFilesyTeacherId(@PathVariable("id") Long id) {
		return teacherService.getFilesyTeacherId(id);
	}

	@GetMapping(path = "/teacher/{id}/substituteCourses")
	public @ResponseBody List<Course> getsubstituteCoursesByTeacherId(@PathVariable("id") Long id) {
		return teacherService.getsubstituteCoursesByTeacherId(id);
	}

	@GetMapping(path = "/teacher/substitutes/{courseId}")
	public @ResponseBody List<Teacher> getSubstitutesByCourseId(@PathVariable("courseId") Long courseId) {
		return teacherService.getSubstitutesByCourseId(courseId);
	}

	@GetMapping(path = "/teachers")
	public @ResponseBody List<Teacher> getAllTeachers() {
		return teacherService.getTeachers();
	}
	
	@GetMapping(path = "/teachersForCourse")
	public @ResponseBody List<TeacherForCourseDTO> getAllTeacherForCourseDTO() {
		return teacherService.getAllTeacherForCourseDTO();
	}
	
	@GetMapping(path = "/{teacherId}/{month}/{year}")
	public @ResponseBody MonthlyTeacherSalaryDTO getTeacherStatistics(@PathVariable("teacherId") Long teacherId, @PathVariable("month") Integer month,@PathVariable("year") Integer year) {
		return teacherService.getTeacherStatistics(teacherId,month,year);
	}
	
	@GetMapping(path = "/admin/teachers")
	public @ResponseBody List<DisplayTeacherDTO> getAllDisplayTeacherDTO() {
		return teacherService.getAllDisplayTeacherDTO();
	}


	@PostMapping(path = "/add")
	public @ResponseBody
	String adminAddTeacher(@RequestBody AddTeacherDTO teacher)  {
		return teacherService.adminAddTeacher(teacher);
	}
	
	
}

