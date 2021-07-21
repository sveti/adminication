package f54148.adminication.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddCourseScheduleDTO {
	
	private LocalDate dayOfTheWeek;
	private LocalTime startTime;
	private LocalTime endTime;
	
}
