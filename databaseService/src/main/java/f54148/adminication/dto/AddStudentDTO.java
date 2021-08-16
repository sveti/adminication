package f54148.adminication.dto;

import f54148.adminication.entity.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddStudentDTO {

    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Gender gender;

}
