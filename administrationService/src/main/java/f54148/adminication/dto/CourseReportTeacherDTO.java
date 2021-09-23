package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseReportTeacherDTO {
    private Long teacherId;
    private String name;
    private Double pricePerLesson;
    private Long subId;
    private String subName;
}
