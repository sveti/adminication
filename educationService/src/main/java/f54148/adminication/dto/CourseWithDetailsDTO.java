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
public class CourseWithDetailsDTO {
	
	private long id;
	private String title;
	private List<LocalTime> startTime;
	private List<LocalTime> endTime;
	private List<LocalDate> startDate;
	private int duration;
	private int signUpLimit;
	private int signedUp;
	private List<String> details;
	private List<String> teachers;
	private String level;

}
