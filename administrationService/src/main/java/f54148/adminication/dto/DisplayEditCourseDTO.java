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
public class DisplayEditCourseDTO {

	private Long id;
	private String title;
	private String description;
	private Integer duration;
	private String level;
	private String status;
	private Integer maxStudents;
	private Integer lessonsPerWeek;
	private Double pricePerStudent;
	List <CourseDetailsDTO> details;
	List<EditCourseScheduleDTO> scheudles;
	List<EditCourseTeacherDTO> teachers;
	
}
