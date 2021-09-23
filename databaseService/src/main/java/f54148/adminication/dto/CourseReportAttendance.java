package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseReportAttendance {
    private Long attendanceId;
    private Long studentId;
    private String studentName;
    private Boolean attended;
}
