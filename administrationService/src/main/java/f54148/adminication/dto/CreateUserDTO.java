package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserDTO {
	
	private long id;
	private String username;
	private String password;
	private String email;
	private String name;
	private String lastName;
	private String gender;

}
