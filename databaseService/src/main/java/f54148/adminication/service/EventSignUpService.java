package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventSignUp;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.EventSignUpRepository;

@Service
public class EventSignUpService {
	
	@Autowired
	private EventSignUpRepository eventSignUpRepository;

	@Autowired
	private EventService eventService;

	@Autowired
	private StudentService studentService;

	public List<EventSignUp> getEventSignUps() {
		List<EventSignUp> eventSignUpList = new ArrayList<>();
		eventSignUpRepository.findAll().forEach(eventSignUpList::add);
		return eventSignUpList;
	}

	public EventSignUp getEventSignUpById(Long eventSignUpId) {
		Optional<EventSignUp> opEventSignUp = eventSignUpRepository.findById(eventSignUpId);
		if (opEventSignUp.isPresent()) {
			return opEventSignUp.get();
		} else {
			return null;
		}
	}

	public boolean addEventSignUp(EventSignUp eventSignUp) {
		if (eventSignUpRepository.save(eventSignUp) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateEventSignUp(EventSignUp eventSignUp) {
		if (eventSignUpRepository.save(eventSignUp) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteEventSignUp(Long eventSignUpId) {
		Optional<EventSignUp> opEventSignUp = eventSignUpRepository.findById(eventSignUpId);
		if (opEventSignUp.isPresent()) {
			EventSignUp ew = opEventSignUp.get();

			Student s = ew.getStudent();
			s.getEventsSignedUp().remove(ew);
			studentService.updateStudent(s);

			Event e = ew.getEvent();
			e.getEventSignedUps().remove(ew);
			eventService.updateEvent(e);

			eventSignUpRepository.deleteById(eventSignUpId);

			return true;
		} else {
			return false;
		}
	}


}
