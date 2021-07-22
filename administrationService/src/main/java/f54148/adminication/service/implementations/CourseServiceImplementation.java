package f54148.adminication.service.implementations;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.AddCourseDTO;
import f54148.adminication.service.CourseService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class CourseServiceImplementation implements CourseService {
	 private final RestTemplate restTemplate;

	@Override
	public String add(AddCourseDTO course) {
		
		ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/courses/add", course, String.class);
		return response.getBody();
	}
}
