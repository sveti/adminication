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
public class StartedCourseDTO {
	
	private long id;
	private String title;
	private List<LocalDate> startDate;
	private List<LocalTime> startTime;
	private List<LocalTime> endTime;
	
}
