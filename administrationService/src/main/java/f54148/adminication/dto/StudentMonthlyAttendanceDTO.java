package f54148.adminication.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentMonthlyAttendanceDTO {

	
	private Long courseId;
	private String courseTitle;
	private List<StudentAttendanceReportDTO> attendances;
	private Double pricePerAttendance;

}
