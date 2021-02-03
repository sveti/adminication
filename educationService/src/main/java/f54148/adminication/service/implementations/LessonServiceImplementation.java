package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.LessonDTO;
import f54148.adminication.dto.UpcommingCourseDTO;
import f54148.adminication.service.LessonService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class LessonServiceImplementation implements LessonService{
	private final RestTemplate restTemplate;

	@Override
	public LessonDTO getLessonDTO(@Min(1) long idLesson) {
		LessonDTO lessonDTO = restTemplate.getForObject("http://databaseService/courses/lessons/{idLesson}/display",LessonDTO.class,idLesson);
		return lessonDTO;
	}

	@Override
	public List<LessonDTO> getLessonsByTeacherIdAndCourseId(@Min(1) long idTeacher, @Min(1) long idCourse) {
		LessonDTO lessons[] = restTemplate.getForObject("http://databaseService/courses/lessons/{idTeacher}/{idCourse}",LessonDTO[].class, idTeacher,idCourse);
		return Arrays.asList(lessons);
	}

	@Override
	public String addLesson(LessonDTO lesson) {
		ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/courses/lessons/add", lesson, String.class);
		return response.getBody();
	}

}
