package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentGradesDTO {
	
	private Long id;
	private Long studentId;
	private Long courseId;
	private String name;
	private String username;
	private float grade;

}
