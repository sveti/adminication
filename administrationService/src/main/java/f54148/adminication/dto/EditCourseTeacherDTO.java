package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EditCourseTeacherDTO{	
	private Long id;
	private Long teacherId;
	private String teacherName;
	private Double salary;

}
