package f54148.adminication.service.implementations;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.MonthlyTeacherSalaryDTO;
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
		


}
