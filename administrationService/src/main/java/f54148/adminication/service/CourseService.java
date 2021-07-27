package f54148.adminication.service;

import f54148.adminication.dto.AddCourseDTO;
import f54148.adminication.dto.DisplayEditCourseDTO;

public interface CourseService {

	String add(AddCourseDTO course);

	DisplayEditCourseDTO getEditCourseDTO(Long idCourse);

	String editCourse(DisplayEditCourseDTO course);

}
