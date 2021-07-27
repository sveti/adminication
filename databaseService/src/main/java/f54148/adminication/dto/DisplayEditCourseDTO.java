package f54148.adminication.dto;

import java.util.List;

import f54148.adminication.entity.CourseStatus;
import f54148.adminication.entity.Level;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DisplayEditCourseDTO {

	private Long id;
	private String title;
	private String description;
	private Integer duration;
	private Level level;
	private CourseStatus status;
	private Integer maxStudents;
	private Integer lessonsPerWeek;
	private Double pricePerStudent;
	List <CourseDetailsDTO> details;
	List<EditCourseScheduleDTO> scheudles;
	List<EditCourseTeacherDTO> teachers;
	
}
