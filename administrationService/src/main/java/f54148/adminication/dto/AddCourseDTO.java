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
public class AddCourseDTO {
	
	private String title;
	private String description;
	private Integer duration;
	private String level;
	private Integer maxStudents;
	private Double pricePerStudent;
	List <String> newCourseDetails;
	List <CourseDetalDTO> details;
	List<AddCourseScheduleDTO> scheudles;
	List<AddCourseTeacherDTO> teachers;

}
