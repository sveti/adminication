package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherOfCourseReport {
    private Long teacherId;
    private String teacherName;
    private Double salary;
    private Integer lessonsPerPeriod;
}
