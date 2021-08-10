package f54148.adminication.service;

import java.util.List;

import f54148.adminication.dto.AddTeacherDTO;
import f54148.adminication.dto.DisplayTeacherDTO;
import f54148.adminication.dto.MonthlyTeacherSalaryDTO;
import f54148.adminication.dto.TeacherForCourseDTO;

public interface TeacherService {

	MonthlyTeacherSalaryDTO getTeacherStatistics(Long teacherId, Integer month, Integer year);

	List<TeacherForCourseDTO> getAllTeacherForCourseDTO();

	List<DisplayTeacherDTO> getAllDisplayTeacherDTO();

	String adminAddTeacher(AddTeacherDTO teacher);
	
}
