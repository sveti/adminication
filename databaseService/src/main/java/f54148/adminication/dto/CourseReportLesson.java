package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CourseReportLesson {
    private Long lessonId;
    private Long teacherId;
    private String teacherName;
    private LocalDate date;
    private String description;
    private List<CourseReportAttendance> attendanceList;
}
