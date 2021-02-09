package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.AttendanceDTO;

public interface AttendanceService {
	
	List<AttendanceDTO> getAttendancesOfLesson(@Min(1) long idLesson);
	List<AttendanceDTO> getAttendancesOfCourse(@Min(1) long idCourse);
	String updateAttendances(List<AttendanceDTO> attendances);
	
}
