package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeachingSalaryDTO {
	
	private Long id;
	private Long courseId;
	private Integer courseSignedUp;
	private String courseTitle;
	private String salaryPerStudent;
	
}
