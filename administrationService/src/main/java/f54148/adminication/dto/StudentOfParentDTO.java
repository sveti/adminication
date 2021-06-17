package f54148.adminication.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentOfParentDTO {
	private long id;
	private String username;
	private String name;
	private String lastName;
	private String gender;

}
