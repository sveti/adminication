package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.GradesOfStudentDTO;
import f54148.adminication.dto.StudentAttendanceDTO;
import f54148.adminication.dto.StudentGradesDTO;

public interface StudentService {
	
	List<StudentAttendanceDTO> getStudentsOfCourse(@Min(1) long idCourse);

	List<StudentGradesDTO> getGradesOfStudentsOfCourse(@Min(1) Long courseId);
	
	List<GradesOfStudentDTO> getGradesOfStudent(@Min(1) Long studentId);

}
