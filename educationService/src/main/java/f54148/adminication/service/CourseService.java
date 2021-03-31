package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.CourseWithDetailsDTO;
import f54148.adminication.dto.FinshedCourseDTO;
import f54148.adminication.dto.StartedCourseDTO;
import f54148.adminication.dto.StartedCourseStudentDTO;
import f54148.adminication.dto.StudentGradesDTO;
import f54148.adminication.dto.UpcommingCourseDTO;

public interface CourseService {
	
	UpcommingCourseDTO getUpcommingCourseById(@Min(1) long idCourse);
	List<UpcommingCourseDTO> getUpcommingCoursesOfTeacher(@Min(1) long idTeacher);
	
	CourseWithDetailsDTO getCourseWithDetails(@Min(1) long idCourse);
	StartedCourseDTO getStartedCourse(@Min(1) long idCourse);
	List<StartedCourseDTO> getStartedCoursesOfTeacher(@Min(1) Long teacherId);
	List<StartedCourseDTO> getSubStartedCoursesByTeacherId(@Min(1) Long teacherId);
	List<FinshedCourseDTO> getFinishedCoursesOfTeacher(@Min(1) Long teacherId);
	String updateGrades(List<StudentGradesDTO> studentGrades);
	List<StartedCourseStudentDTO> getStartedCoursesOfStudent(@Min(1) Long studentId);
	List<UpcommingCourseDTO> getUpcommingCoursesByStudentId(@Min(1) Long studentId);

}
