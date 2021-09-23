package f54148.adminication.service;

import java.util.List;

import f54148.adminication.dto.AddCourseDTO;
import f54148.adminication.dto.CourseReportDTO;
import f54148.adminication.dto.CourseTitlesDTO;
import f54148.adminication.dto.DisplayEditCourseDTO;

public interface CourseService {

	String add(AddCourseDTO course);

	DisplayEditCourseDTO getEditCourseDTO(Long idCourse);

	String editCourse(DisplayEditCourseDTO course);

	List<CourseTitlesDTO> getCourseTitles();

    CourseReportDTO getCourseReport(Long idCourse);

    List<CourseTitlesDTO> getAllCourseTitles();
}
