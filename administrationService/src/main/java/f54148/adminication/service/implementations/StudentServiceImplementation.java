package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.StudentMonthlyAttendanceDTO;
import f54148.adminication.service.StudentService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class StudentServiceImplementation implements StudentService {
	
	 private final RestTemplate restTemplate;

	@Override
	public List<StudentMonthlyAttendanceDTO> getStudentReport(Long studentId, Integer month, Integer year) {
		StudentMonthlyAttendanceDTO statistics[] = restTemplate.getForObject("http://databaseService/students/{teacherId}/{month}/{year}",StudentMonthlyAttendanceDTO[].class, studentId,month,year);
		return Arrays.asList(statistics);
	}

}
