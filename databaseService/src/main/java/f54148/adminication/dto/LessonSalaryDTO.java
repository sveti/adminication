package f54148.adminication.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LessonSalaryDTO {
	
	private Long id;
	private Long courseId;
	private LocalDate date;
	private Integer attended;

}
