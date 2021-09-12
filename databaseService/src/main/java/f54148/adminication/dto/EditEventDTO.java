package f54148.adminication.dto;

import f54148.adminication.entity.CourseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EditEventDTO {
    private Long id;
    private String title;
    private Integer minAge;
    private Integer maxAge;
    private Integer maxNumberOfPeople;
    private CourseStatus status;
    private String description;
    List<EditCourseScheduleDTO> schedules;
}
