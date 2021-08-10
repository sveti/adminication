package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.AddTeacherDTO;
import f54148.adminication.dto.DisplayTeacherDTO;
import f54148.adminication.dto.MonthlyTeacherSalaryDTO;
import f54148.adminication.dto.TeacherForCourseDTO;
import f54148.adminication.service.TeacherService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class TeacherServiceImplementation implements TeacherService {
	
	 private final RestTemplate restTemplate;

	@Override
	public MonthlyTeacherSalaryDTO getTeacherStatistics(Long teacherId, Integer month, Integer year) {
		MonthlyTeacherSalaryDTO statistics = restTemplate.getForObject("http://databaseService/teachers/{teacherId}/{month}/{year}",MonthlyTeacherSalaryDTO.class, teacherId,month,year);
		return statistics;
	}

	@Override
	public List<TeacherForCourseDTO> getAllTeacherForCourseDTO() {
		TeacherForCourseDTO teachers[] = restTemplate.getForObject("http://databaseService/teachers/teachersForCourse",TeacherForCourseDTO[].class);
		return Arrays.asList(teachers);
	}

	@Override
	public List<DisplayTeacherDTO> getAllDisplayTeacherDTO() {
		DisplayTeacherDTO teachers[] = restTemplate.getForObject("http://databaseService/teachers/admin/teachers",DisplayTeacherDTO[].class);
		return Arrays.asList(teachers);
	}

	@Override
	public String adminAddTeacher(AddTeacherDTO teacher) {
		ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/teachers/add", teacher, String.class);
		return response.getBody();
	}
		


}
