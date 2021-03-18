package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FinshedCourseDTO {

	private long id;
	private String title;
	private int signedUp;
	private int numberOfSetGrades;
	
}
