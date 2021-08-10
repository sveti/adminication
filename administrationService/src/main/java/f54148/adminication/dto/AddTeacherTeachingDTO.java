package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddTeacherTeachingDTO {
	
	private Long courseId;
	private Double salary;
	private Long substituteId;
}
