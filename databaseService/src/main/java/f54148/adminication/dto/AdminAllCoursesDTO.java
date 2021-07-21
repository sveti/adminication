package f54148.adminication.dto;

import f54148.adminication.entity.CourseStatus;
import f54148.adminication.entity.Level;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminAllCoursesDTO {
	
	private Long id;
	private String title;
	private Double pricePerStudent;
	private Integer maxStudents;
	private CourseStatus status;
	private Level level;
	private Integer duration;
	private Integer signedUp;
	private Integer waitingList;

}
