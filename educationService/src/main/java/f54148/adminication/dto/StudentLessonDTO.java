package f54148.adminication.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentLessonDTO {
	
	private Long id;
	private LocalDate date;
	private String description;
	private Boolean attended;
	
}
