package f54148.adminication.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

}
