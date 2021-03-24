package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditUserDTO {
	
	private long id;
	private String username;
	private String name;
	private String lastName;
	private String email;
	private String password;
	private Boolean changedPassword;

}
