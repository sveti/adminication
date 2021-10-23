package f54148.adminication.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentScheduleDTO {
	private long id;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate startDate;
	private int duration;
}
