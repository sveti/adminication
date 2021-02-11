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

import f54148.adminication.dto.AttendanceDTO;
import f54148.adminication.service.AttendanceService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class AttendanceServiceImplementation implements AttendanceService {

	private final RestTemplate restTemplate;
	
	@Override
	public List<AttendanceDTO> getAttendancesOfLesson(@Min(1) long idLesson) {
		AttendanceDTO lessons[] = restTemplate.getForObject("http://databaseService/courses/lessons/{idLesson}/attendance",AttendanceDTO[].class,idLesson);
		return Arrays.asList(lessons);
	}

	@Override
	public List<AttendanceDTO> getAttendancesOfCourse(@Min(1) long idCourse) {
		AttendanceDTO lessons[] = restTemplate.getForObject("http://databaseService/courses/{idCourse}/attendance",AttendanceDTO[].class,idCourse);
		return Arrays.asList(lessons);
	}

	@Override
	public String updateAttendances(List<AttendanceDTO> attendances) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<AttendanceDTO>> requestEntity = new HttpEntity<>(attendances, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://databaseService/courses/attendances/update", HttpMethod.PUT,requestEntity,String.class);
		return response.getBody();
	}

	@Override
	public String addAttendances(List<AttendanceDTO> attendances) {
		 ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/courses/attendances/add",attendances,String.class);
		return response.getBody();
	}
	
	

}
