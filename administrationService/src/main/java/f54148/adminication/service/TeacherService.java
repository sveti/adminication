package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.CreateUserDTO;

public interface TeacherService {
	
	List<CreateUserDTO> getTeachers();
	
	CreateUserDTO getTeacher(@Min(1) long id);
	
	String createTeacher(CreateUserDTO teacherDTO);

}
