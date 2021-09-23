package f54148.adminication.dto;


import f54148.adminication.entity.CourseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CourseReportDTO {

    private Long courseId;
    private String title;
    private String description;
    private Double pricePerStudent;
    private List<CourseReportStudentsSignedUpDTO> studentsSignedUp;
    private List<CourseReportStudentsSignedUpDTO> studentsWaitingList;
    private List<CourseReportLesson> courseReportLessons;
    private List<CourseReportTeacherDTO> teachers;
    private CourseStatus status;
    private List<FinalGradesDTO> finalGradesDTO;
}
