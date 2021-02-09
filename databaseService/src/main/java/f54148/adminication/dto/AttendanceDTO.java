package f54148.adminication.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceDTO {
	
	private Long id;
	private Long studentId;
	private Long lessonId;
	private boolean attended;

}
