package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseOfStudentInWaitingListDTO {
	
	private Long courseWaitingListId;
	private Long studentId;
	private Long courseId;
	private Integer numberInLine;

}	
