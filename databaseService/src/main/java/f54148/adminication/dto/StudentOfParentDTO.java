package f54148.adminication.dto;

import f54148.adminication.entity.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentOfParentDTO {
	private Long id;
	private String username;
	private String name;
	private String lastName;
	private Gender gender;

}
