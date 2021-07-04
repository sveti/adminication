package f54148.adminication.service;

import f54148.adminication.dto.AddEventSignUpDTO;

public interface EventSignUpService {
	String addAddEventSignUpDTO(AddEventSignUpDTO dto);

	String deleteEventSignUpByStudentAndCourse(Long studentId, Long eventId);
}
