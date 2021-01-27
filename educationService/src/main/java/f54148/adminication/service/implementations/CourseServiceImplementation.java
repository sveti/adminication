package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.CourseWithDetailsDTO;
import f54148.adminication.dto.StartedCourseDTO;
import f54148.adminication.dto.UpcommingCourseDTO;
import f54148.adminication.service.CourseService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class CourseServiceImplementation  implements CourseService{
	
	private final RestTemplate restTemplate;
	
	@Override
	public UpcommingCourseDTO getUpcommingCourseOfTeacher(long idCourse) {
		UpcommingCourseDTO upcommingCourse = restTemplate.getForObject("http://databaseService/courses/upcomming/{idCourse}",UpcommingCourseDTO.class,idCourse);
		return upcommingCourse;
	}

	@Override
	public List<UpcommingCourseDTO> getUpcommingCoursesOfTeacher(@Min(1) long idTeacher) {
		UpcommingCourseDTO upcommingCourses[] = restTemplate.getForObject("http://databaseService/courses/{idTeacher}/upcomming",UpcommingCourseDTO[].class, idTeacher);
		return Arrays.asList(upcommingCourses);
	}

	@Override
	public CourseWithDetailsDTO getCourseWithDetails(@Min(1) long idCourse) {
		CourseWithDetailsDTO courseWithDetails = restTemplate.getForObject("http://databaseService/courses/details/{idCourse}",CourseWithDetailsDTO.class,idCourse);
		return courseWithDetails;
	}

	@Override
	public StartedCourseDTO getStartedCourse(@Min(1) long idCourse) {
		StartedCourseDTO startedCourse = restTemplate.getForObject("http://databaseService/courses/started/{idCourse}",StartedCourseDTO.class,idCourse);
		return startedCourse;
	}

	@Override
	public List<StartedCourseDTO> getStartedCoursesOfTeacher(@Min(1) Long teacherId) {
		StartedCourseDTO startedCourses[] = restTemplate.getForObject("http://databaseService/courses/{idTeacher}/started",StartedCourseDTO[].class, teacherId);
		return Arrays.asList(startedCourses);
	}

	@Override
	public List<StartedCourseDTO> getSubStartedCoursesByTeacherId(@Min(1) Long teacherId) {
		StartedCourseDTO subStartedCourses[] = restTemplate.getForObject("http://databaseService/courses/{idTeacher}/sub/started",StartedCourseDTO[].class, teacherId);
		return Arrays.asList(subStartedCourses);
	}

}
