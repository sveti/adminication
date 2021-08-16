package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddParentDTO {
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String gender;
    private List<AddStudentDTO> students;
}
