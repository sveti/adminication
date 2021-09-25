package f54148.adminication.service;

import java.util.List;

import f54148.adminication.dto.*;

public interface CourseService {

	String add(AddCourseDTO course);

	DisplayEditCourseDTO getEditCourseDTO(Long idCourse);

	String editCourse(EditCourseDTO course);

	List<CourseTitlesDTO> getCourseTitles();

    CourseReportDTO getCourseReport(Long idCourse);

    List<CourseTitlesDTO> getAllCourseTitles();

    String beginCourse(Long idCourse);

    String finishCourse(Long idCourse);
}
