package f54148.adminication.controller;

import java.util.HashSet;
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

import f54148.adminication.dto.AttendanceDTO;
import f54148.adminication.dto.CourseWithDetailsDTO;
import f54148.adminication.dto.StartedCourseDTO;
import f54148.adminication.dto.StudentAttendanceDTO;
import f54148.adminication.dto.UpcommingCourseDTO;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseDetail;
import f54148.adminication.entity.File;
import f54148.adminication.entity.Lesson;
import f54148.adminication.entity.Schedule;
import f54148.adminication.entity.Student;
import f54148.adminication.entity.Teacher;
import f54148.adminication.service.CourseDetailService;
import f54148.adminication.service.CourseService;
import f54148.adminication.service.LessonService;
import f54148.adminication.service.ScheduleService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/courses")
public class CourseController {

	
	private final CourseService courseService;

	private final CourseDetailService cdService;

	private final ScheduleService sService;
	
	private final LessonService lessonService;

	@PostMapping(path = "/addCourse")
	public @ResponseBody String addNewCourse(@RequestBody Course course) {

		// get schedule and courses from passed argument
		Set<Schedule> shedule = course.getCourseSchedule();
		Set<CourseDetail> details = course.getDetails();

		Set<Schedule> pshedule = new HashSet<Schedule>();
		Set<CourseDetail> pdetails = new HashSet<CourseDetail>();

		course.setCourseSchedule(null);
		course.setDetails(null);

		// persist details first
		for (CourseDetail cd : details) {
			if (cd.getId() == null) {
				cdService.addCourseDetail(cd);
			}
			pdetails.add(cd);
		}

		// persist schedule
		for (Schedule s : shedule) {
			if (s.getId() == null) {
				sService.addSchedule(s);
			}
			pshedule.add(s);
		}

		// add course to database
		if (courseService.addCourse(course)) {
			// add schedule and details and then persist
			course.setCourseSchedule(pshedule);
			course.setDetails(pdetails);
			updateCourse(course);
			return "Added course!";
		} else {
			return "An error has occured";
		}

	}

	@GetMapping(path = "/courses")
	public @ResponseBody List<Course> getAllCourses() {
		return courseService.getCourses();
	}

	@PutMapping(path = "/updateCourse")
	public @ResponseBody String updateCourse(@RequestBody Course course) {

		if (courseService.addCourse(course)) {
			return "Updated course";
		} else {
			return "An error has occured";
		}

	}
	

	@GetMapping(path = "/course/{id}")
	public @ResponseBody Course getCourseById(@PathVariable("id") Long id) {
		return courseService.getCourseById(id);
	}
	
	@GetMapping(path = "/course/{id}/courseDetails")
	public @ResponseBody Set<CourseDetail> getCourseDetailsByCourseId(@PathVariable("id") Long id) {
		return courseService.getCourseDetailsByCourseId(id);
	}

	@GetMapping(path = "/course/{id}/students")
	public @ResponseBody List<Student> getStudentsByCourseId(@PathVariable("id") Long id) {
		return courseService.getStudentsByCourseId(id);
	}

	@GetMapping(path = "/course/{id}/teachers")
	public @ResponseBody List<Teacher> getTeachersByCourseId(@PathVariable("id") Long id) {
		return courseService.getTeachersByCourseId(id);
	}

	
	@GetMapping(path = "/course/{id}/substitutes")
	public @ResponseBody List<Teacher> getSubstitutesByCourseId(@PathVariable("id") Long id) {
		return courseService.getSubstitutesByCourseId(id);
	}

	@GetMapping(path = "/course/{id}/schedule")
	public @ResponseBody Set<Schedule> getScheduleByCourseId(@PathVariable("id") Long id) {
		return courseService.getScheduleByCourseId(id);
	}
	
	@GetMapping(path = "/course/{id}/files")
	public @ResponseBody Set<File> getFilesByCourseId(@PathVariable("id") Long id) {
		return courseService.getFilesByCourseId(id);
	}
	
	@GetMapping(path = "/course/{id}/students/waiting")
	public @ResponseBody List<Student> getStudentsWaitingByCourseId(@PathVariable("id") Long id) {
		return courseService.getStudentsWaitingByCourseId(id);
	}

	@GetMapping(path = "/course/{id}/lessons")
	public @ResponseBody Set<Lesson> getLessonsByCourseId(@PathVariable("id") Long id) {
		return courseService.getLessonsByCourseId(id);
	}
	
	@GetMapping(path = "/upcomming/{idCourse}")
	public @ResponseBody UpcommingCourseDTO getUpcommingCourseDTOById(@PathVariable("idCourse") Long idCourse) {
		
		return courseService.getUpcommingCourseDTOById(idCourse);
	}
	
	@GetMapping(path = "/started/{idCourse}")
	public @ResponseBody StartedCourseDTO getStartedCourseDTOById(@PathVariable("idCourse") Long idCourse) {
		
		return courseService.getStartedCourseDTOById(idCourse);
	}
	
	@GetMapping(path = "/{idTeacher}/upcomming")
	public @ResponseBody List<UpcommingCourseDTO> getUpcommingCoursesDTOByTeacherId(@PathVariable("idTeacher") Long idTeacher) {
		
		return courseService.getUpcommingCoursesDTOByTeacherId(idTeacher);
	}
	
	@GetMapping(path = "/{idTeacher}/started")
	public @ResponseBody List<StartedCourseDTO> getStartedCourseDTOByTeacherId(@PathVariable("idTeacher") Long idTeacher) {
		
		return courseService.getStartedCourseDTOByTeacherId(idTeacher);
	}
	
	@GetMapping(path = "/{idTeacher}/sub/started")
	public @ResponseBody List<StartedCourseDTO> getSubStartedCourseDTOByTeacherId(@PathVariable("idTeacher") Long idTeacher) {
		
		return courseService.getSubStartedCourseDTOByTeacherId(idTeacher);
	}
	
	@GetMapping(path = "/details/{idCourse}")
	public @ResponseBody CourseWithDetailsDTO getCourseWithDetailsDTOById(@PathVariable("idCourse") Long idCourse) {
		
		return courseService.getCourseWithDetailsDTO(idCourse);
	}
	
	
	@GetMapping(path = "/{idCourse}/students")
	public @ResponseBody List<StudentAttendanceDTO> StudentAttendanceDTObyCourseId(@PathVariable("idCourse") Long idCourse) {
		
		return courseService.getStudentAttendanceDTOOfCourse(idCourse);
	}
	
	@GetMapping(path = "/{idCourse}/attendance")
	public @ResponseBody List<AttendanceDTO> getAttendanceDTOByLessonId(@PathVariable("idCourse") Long idCourse) {
		return lessonService.getAttendanceDTOOfCourse(idCourse);
	}
	

	
}
