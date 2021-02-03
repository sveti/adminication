package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.StudentAttendanceDTO;

public interface StudentService {
	
	List<StudentAttendanceDTO> getStudentsOfCourse(@Min(1) long idCourse);

}
