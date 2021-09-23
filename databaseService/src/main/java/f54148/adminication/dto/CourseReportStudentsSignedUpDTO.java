package f54148.adminication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CourseReportStudentsSignedUpDTO {

    private Long enrollmentId;
    private Long studentId;
    private String studentName;
}
