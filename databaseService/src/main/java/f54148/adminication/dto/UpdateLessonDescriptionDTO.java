package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdateLessonDescriptionDTO {
	
	private Long id;
	private String description;
}
