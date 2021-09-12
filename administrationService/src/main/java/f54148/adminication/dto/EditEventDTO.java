package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String status;
    private String description;
    List<EditCourseScheduleDTO> schedules;
}
