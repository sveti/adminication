package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EventReportDTO {
    private Long eventId;
    private String title;
    private String description;
    private Integer minAge;
    private Integer maxAge;
    private Integer maxNumberOfPeople;
    private List<CourseReportStudentsSignedUpDTO> studentsSignedUp;
    private List<CourseReportStudentsSignedUpDTO> studentsWaitingList;
    private String status;
}
