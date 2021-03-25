package f54148.adminication.service;

import f54148.adminication.dto.MonthlyTeacherSalaryDTO;

public interface TeacherService {

	MonthlyTeacherSalaryDTO getTeacherStatistics(Long teacherId, Integer month, Integer year);
	
}
