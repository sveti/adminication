package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.LessonDTO;
import f54148.adminication.dto.UpdateLessonDescriptionDTO;
import f54148.adminication.service.LessonService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class LessonServiceImplementation implements LessonService{
	private final RestTemplate restTemplate;

	@Override
	public LessonDTO getLessonDTO(@Min(1) long idLesson) {
		return restTemplate.getForObject("http://databaseService/courses/lessons/{idLesson}/display",LessonDTO.class,idLesson);
	}



	@Override
	public String addLesson(LessonDTO lesson) {
		ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/courses/lessons/add", lesson, String.class);
		return response.getBody();
	}

	@Override
	public String updateLessonDescription(UpdateLessonDescriptionDTO updateLessonDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UpdateLessonDescriptionDTO> requestEntity = new HttpEntity<>(updateLessonDTO, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://databaseService/courses/lessons/updateDescription", HttpMethod.PUT,requestEntity,String.class);
		return response.getBody();
	}

	@Override
	public List<LessonDTO> getLessonsByCourseId(@Min(1) Long courseId) {
		LessonDTO[] lessons = restTemplate.getForObject("http://databaseService/courses/lessons/{courseId}/lessons",LessonDTO[].class, courseId);
		assert lessons != null;
		return Arrays.asList(lessons);
	}

	
}
