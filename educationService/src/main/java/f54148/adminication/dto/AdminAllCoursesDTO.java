package f54148.adminication.dto;

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
	private String status;
	private String level;
	private Integer duration;
	private Integer signedUp;
	private Integer waitingList;

}
