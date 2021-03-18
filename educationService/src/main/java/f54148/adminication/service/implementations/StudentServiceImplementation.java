package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.StudentAttendanceDTO;
import f54148.adminication.dto.StudentGradesDTO;
import f54148.adminication.service.StudentService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class StudentServiceImplementation implements StudentService {
	
	private final RestTemplate restTemplate;

	@Override
	public List<StudentAttendanceDTO> getStudentsOfCourse(@Min(1) long idCourse) {
		StudentAttendanceDTO students[] = restTemplate.getForObject("http://databaseService/courses/{idCourse}/students",StudentAttendanceDTO[].class, idCourse);
		return Arrays.asList(students);
	}

	@Override
	public List<StudentGradesDTO> getGradesOfStudentsOfCourse(@Min(1) Long courseId) {
		StudentGradesDTO students[] = restTemplate.getForObject("http://databaseService/enrollments/{courseId}/grades",StudentGradesDTO[].class, courseId);
		return Arrays.asList(students);
	}

}
