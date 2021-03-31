package f54148.adminication.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StartedCourseStudentDTO {
	
	private Long id;
	private String title;
	private List<LocalDate> days;
	private List<LocalTime> startTime;
	private List<LocalTime> endTime;
	private List<StudentLessonDTO> lessons;
	
	public StartedCourseStudentDTO(StartedCourseDTO dto) {
		super();
		id = dto.getId();
		title = dto.getTitle();
		startTime = dto.getStartTime();
		endTime = dto.getEndTime();
		days = dto.getStartDate();
	}
	
	

}
