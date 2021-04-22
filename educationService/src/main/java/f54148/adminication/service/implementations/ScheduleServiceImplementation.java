package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.StudentScheduleDTO;
import f54148.adminication.service.ScheduleService;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
@Validated
public class ScheduleServiceImplementation implements ScheduleService{
	private final RestTemplate restTemplate;
	
	@Override
	public List<StudentScheduleDTO> getStudentSchedule(@Min(1) Long studentId) {
		StudentScheduleDTO schedules[] = restTemplate.getForObject("http://databaseService/students/{studentId}/schedule",StudentScheduleDTO[].class,studentId);
		return Arrays.asList(schedules);
	}



}
