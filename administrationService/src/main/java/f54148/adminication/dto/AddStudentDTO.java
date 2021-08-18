package f54148.adminication.dto;

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
    private String gender;
    private String role;

}
