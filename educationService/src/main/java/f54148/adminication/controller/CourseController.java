package f54148.adminication.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import f54148.adminication.dto.AdminAllCoursesDTO;
import f54148.adminication.dto.AttendanceDTO;
import f54148.adminication.dto.CourseDetailsDTO;
import f54148.adminication.dto.CourseWithDetailsDTO;
import f54148.adminication.dto.FinshedCourseDTO;
import f54148.adminication.dto.StartedCourseDTO;
import f54148.adminication.dto.StartedCourseStudentDTO;
import f54148.adminication.dto.StudentAttendanceDTO;
import f54148.adminication.dto.StudentGradesDTO;
import f54148.adminication.dto.UpcommingCourseDTO;
import f54148.adminication.service.AttendanceService;
import f54148.adminication.service.CourseService;
import f54148.adminication.service.StudentService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/courses")
public class CourseController {

	private final CourseService courseService;
	private final StudentService studentService;
	private final AttendanceService attendanceService;
	
	@GetMapping(path = "/upcomming/{courseId}")
	public @ResponseBody UpcommingCourseDTO getUpcommingCourseById(@PathVariable("courseId") @Min(1) Long courseId) {
		return courseService.getUpcommingCourseById(courseId);
	}
	
	@GetMapping(path = "/teacher/{teacherId}/upcomming")
	public @ResponseBody List<UpcommingCourseDTO> getUpcommingCoursesByTeacherId(@PathVariable("teacherId") @Min(1) Long teacherId) {
		return courseService.getUpcommingCoursesOfTeacher(teacherId);
	}
	@GetMapping(path = "/teacher/{teacherId}/finished")
	public @ResponseBody List<FinshedCourseDTO> getFinishedCoursesByTeacherId(@PathVariable("teacherId") @Min(1) Long teacherId) {
		return courseService.getFinishedCoursesOfTeacher(teacherId);
	}

	@GetMapping(path = "/teacher/{teacherId}/started")
	public @ResponseBody List<StartedCourseDTO> getStartedCoursesByTeacherId(@PathVariable("teacherId") @Min(1) Long teacherId) {
		return courseService.getStartedCoursesOfTeacher(teacherId);
	}
	
	@GetMapping(path = "/student/{studentId}/started")
	public @ResponseBody List<StartedCourseStudentDTO> getStartedCoursesByStudentId(@PathVariable("studentId") @Min(1) Long studentId) {
		return courseService.getStartedCoursesOfStudent(studentId);
	}
	
	@GetMapping(path = "/student/{studentId}/upcomming")
	public @ResponseBody List<UpcommingCourseDTO> getUpcommingCoursesByStudentId(@PathVariable("studentId") @Min(1) Long studentId) {
		return courseService.getUpcommingCoursesByStudentId(studentId);
	}


	@GetMapping(path = "/details/{courseId}")
	public @ResponseBody CourseWithDetailsDTO getCourseWithDetailsDTOById(@PathVariable("courseId") @Min(1) Long courseId) {
		return courseService.getCourseWithDetails(courseId);
	}
	
	@GetMapping(path = "/allCourses")
	public @ResponseBody List<CourseWithDetailsDTO> getAllCourseWithDetailsDTO() {
		return courseService.getAllCourseWithDetailsDTO();
	}
	
	@GetMapping(path = "/started/{courseId}")
	public @ResponseBody StartedCourseDTO getStartedCourseById(@PathVariable("courseId") @Min(1) Long courseId) {
		return courseService.getStartedCourse(courseId);
	}
	
	@GetMapping(path = "/teacher/{teacherId}/sub/started")
	public @ResponseBody List<StartedCourseDTO> getSubStartedCoursesByTeacherId(@PathVariable("teacherId") @Min(1) Long teacherId) {
		return courseService.getSubStartedCoursesByTeacherId(teacherId);
	}
	
	@GetMapping(path = "/{courseId}/students")
	public @ResponseBody List<StudentAttendanceDTO> getStudentAttendanceDTOByCourseId(@PathVariable("courseId") @Min(1) Long courseId) {
		return studentService.getStudentsOfCourse(courseId);
	}
	
	@GetMapping(path = "/{courseId}/studentsGrades")
	public @ResponseBody List<StudentGradesDTO> getStudentGradesDTOByCourseId(@PathVariable("courseId") @Min(1) Long courseId) {
		return studentService.getGradesOfStudentsOfCourse(courseId);
	}

	@GetMapping(path = "/{courseId}/attendance")
	public @ResponseBody List<AttendanceDTO> getStudentGradesDTO(@PathVariable("courseId") @Min(1) Long courseId) {
		return attendanceService.getAttendancesOfCourse(courseId);
	}
	
	
	@PutMapping(path = "/updateGrades")
	public @ResponseBody String updateGrades(@RequestBody List<StudentGradesDTO> studentGrades) {
		return courseService.updateGrades(studentGrades);
	}
	
	@GetMapping(path = "/courseDetails")
	public @ResponseBody List<CourseDetailsDTO> getAllCourseDetails() {
		return courseService.getAllCourseDetails();
	}
	

	@GetMapping(path = "/admin/courses")
	public @ResponseBody List<AdminAllCoursesDTO> getAllCourses() {
		return courseService.getAllCourses();
	}
	
	

}
