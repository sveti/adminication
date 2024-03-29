package f54148.adminication.service.implementations;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.AddEnrollmentDTO;
import f54148.adminication.service.EnrollmentService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class EnrollmentServiceImplementation implements EnrollmentService{
	
	private final RestTemplate restTemplate;

	@Override
	public String addEnrollment(AddEnrollmentDTO dto) {
		ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/enrollments/add", dto, String.class);
		return response.getBody();
	}

	@Override
	public String deleteEnrollmentByStudentAndCourse(Long studentId, Long courseId) {
		try {
			restTemplate.delete("http://databaseService/enrollments/delete/" + studentId + "/"+courseId);
			return "You have successfully unsubscribed from this course!";
		}
		catch (HttpClientErrorException e) {
			return "An error has occured!";
		}
	}

}
