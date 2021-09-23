package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import f54148.adminication.dto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

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

	@Override
	public DisplayEditCourseDTO getEditCourseDTO(Long idCourse) {
		return restTemplate.getForObject("http://databaseService/courses/edit/{idCourse}",DisplayEditCourseDTO.class, idCourse);
	}

	@Override
	public String editCourse(DisplayEditCourseDTO course) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DisplayEditCourseDTO> requestEntity = new HttpEntity<>(course, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://databaseService/courses/edit", HttpMethod.PUT,requestEntity,String.class);
		return response.getBody();
	}

	@Override
	public List<CourseTitlesDTO> getCourseTitles() {
		CourseTitlesDTO[] courses = restTemplate.getForObject("http://databaseService/courses/titles", CourseTitlesDTO[].class);
		assert courses != null;
		return Arrays.asList(courses);
	}

	@Override
	public CourseReportDTO getCourseReport(Long idCourse) {
		return restTemplate.getForObject("http://databaseService/courses/report/{idCourse}",CourseReportDTO.class, idCourse);

	}

	@Override
	public List<CourseTitlesDTO> getAllCourseTitles() {
		CourseTitlesDTO[] courses = restTemplate.getForObject("http://databaseService/courses/titles/all", CourseTitlesDTO[].class);
		assert courses != null;
		return Arrays.asList(courses);
	}
}
