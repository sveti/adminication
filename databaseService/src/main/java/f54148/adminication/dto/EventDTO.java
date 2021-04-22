package f54148.adminication.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import f54148.adminication.entity.CourseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventDTO {
	
	private Long id;
	private String title;
	private Integer minAge;
	private Integer maxAge;
	private Integer maxNumberOfPeople;
	private Integer signedUp;
	private CourseStatus status;
	private String description;
	private List<LocalTime> startTime;
	private List<LocalTime> endTime;
	private List<LocalDate> startDate;

}
