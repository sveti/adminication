package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddEventDTO {

    private String title;
    private String description;
    private Integer minAge;
    private Integer maxAge;
    private Integer maxNumberOfPeople;
    List<AddCourseScheduleDTO> schedules;
}
