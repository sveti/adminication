package f54148.adminication.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditCourseDTO extends DisplayEditCourseDTO{
	List <String> newCourseDetails;
}
