package f54148.adminication.service;

import java.util.List;

import f54148.adminication.dto.StudentMonthlyAttendanceDTO;

public interface StudentService {

	List<StudentMonthlyAttendanceDTO> getStudentReport(Long studentId, Integer month, Integer year);

}
