package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.LessonDTO;
import f54148.adminication.dto.UpdateLessonDescriptionDTO;

public interface LessonService {
	LessonDTO getLessonDTO(@Min(1) long idLesson);
	String addLesson(LessonDTO lesson);
	String updateLessonDescription(UpdateLessonDescriptionDTO updateLessonDTO);
	List<LessonDTO> getLessonsByCourseId(@Min(1) Long courseId);

}
