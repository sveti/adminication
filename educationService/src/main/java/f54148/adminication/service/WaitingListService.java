package f54148.adminication.service;

import java.util.List;

import f54148.adminication.dto.AddEventWaitingListDTO;
import f54148.adminication.dto.AddWaitingListSignUp;
import f54148.adminication.dto.CourseOfStudentInWaitingListDTO;
import f54148.adminication.dto.EventOfStudentInWaitingListDTO;

public interface WaitingListService {
	
	String addWaitingListSignUp(AddWaitingListSignUp dto);
	List<CourseOfStudentInWaitingListDTO> getWaitingListCoursesOfStudent(Long studentId);
	String deleteCourseWaitingList(Long courseWaitingListId);
	
	List<EventOfStudentInWaitingListDTO> getWaitingListEventsOfStudent(Long studentId);
	String addAddEventWaitingListSignUp(AddEventWaitingListDTO dto);
	String deleteEventWaitingList(Long eventWaitingListId);
	
}
