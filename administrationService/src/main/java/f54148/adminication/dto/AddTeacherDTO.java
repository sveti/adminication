package f54148.adminication.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddTeacherDTO {
	
	private String name;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private String gender;
	private String role;
	private List<AddTeacherTeachingDTO> teachings;
	

}
