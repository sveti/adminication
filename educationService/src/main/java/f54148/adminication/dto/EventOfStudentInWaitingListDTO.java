package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventOfStudentInWaitingListDTO {
	private Long eventWaitingListId;
	private Long studentId;
	private Long eventId;
	private Integer numberInLine;
}
