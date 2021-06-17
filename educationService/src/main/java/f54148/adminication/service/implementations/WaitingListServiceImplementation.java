package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.AddEventWaitingListDTO;
import f54148.adminication.dto.AddWaitingListSignUp;
import f54148.adminication.dto.AttendanceDTO;
import f54148.adminication.dto.CourseOfStudentInWaitingListDTO;
import f54148.adminication.dto.EventOfStudentInWaitingListDTO;
import f54148.adminication.dto.StudentScheduleDTO;
import f54148.adminication.service.WaitingListService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class WaitingListServiceImplementation implements WaitingListService{

	private final RestTemplate restTemplate;
	
	@Override
	public String addWaitingListSignUp(AddWaitingListSignUp dto) {
		ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/courses/courseWaitingLists/add", dto, String.class);
		return response.getBody();
	}

	@Override
	public List<CourseOfStudentInWaitingListDTO> getWaitingListCoursesOfStudent(Long studentId) {
		CourseOfStudentInWaitingListDTO dtos[] = restTemplate.getForObject("http://databaseService/courses/courseWaitingLists/student/{studentId}/waitingList",CourseOfStudentInWaitingListDTO[].class,studentId);
		return Arrays.asList(dtos);
	}

	@Override
	public String deleteCourseWaitingList(Long courseWaitingListId) {
		try {
			restTemplate.delete("http://databaseService/courses/courseWaitingLists/courseWaitingList/" + courseWaitingListId);
			return "Successfully left course waiting list!";
		}
		catch (HttpClientErrorException e) {
			return "An error has occured!";
		}
		
	}

	@Override
	public List<EventOfStudentInWaitingListDTO> getWaitingListEventsOfStudent(Long studentId) {
		EventOfStudentInWaitingListDTO dtos[] = restTemplate.getForObject("http://databaseService/events/eventWaitingLists/student/{studentId}/waitingList",EventOfStudentInWaitingListDTO[].class,studentId);
		return Arrays.asList(dtos);
	}

	@Override
	public String addAddEventWaitingListSignUp(AddEventWaitingListDTO dto) {
		ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/events/eventWaitingLists/add", dto, String.class);
		return response.getBody();
	}

	@Override
	public String deleteEventWaitingList(Long eventWaitingListId) {
		try {
			restTemplate.delete("http://databaseService/events/eventWaitingLists/eventWaitingList/" + eventWaitingListId);
			return "Successfully left event waiting list!";
		}
		catch (HttpClientErrorException e) {
			return "An error has occured!";
		}
	}

}
