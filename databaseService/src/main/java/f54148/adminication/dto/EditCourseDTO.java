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
public class EditCourseDTO extends DisplayEditCourseDTO{
	List <String> newCourseDetails;
}
