package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.StudentScheduleDTO;

public interface ScheduleService {
	List<StudentScheduleDTO> getStudentSchedule(@Min(1) Long studentId);
}
