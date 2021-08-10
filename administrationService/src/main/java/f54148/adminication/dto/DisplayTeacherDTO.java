package f54148.adminication.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DisplayTeacherDTO {
	
	private long id;
	private String name;
	private String gender;
	private long teaching;
	private long substituting;
}
