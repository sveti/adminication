package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventSignUp;
import f54148.adminication.entity.EventWaitingList;
import f54148.adminication.entity.Schedule;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.EventRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService {

	private final EventRepository eventRepository;

	public List<Event> getEvents() {
		List<Event> eventList = new ArrayList<>();
		eventRepository.findAll().forEach(eventList::add);
		return eventList;
	}

	public Event getEventById(Long eventId) {
		Optional<Event> opEvent = eventRepository.findById(eventId);
		if (opEvent.isPresent()) {
			return opEvent.get();
		} else {
			return null;
		}
	}

	public boolean addEvent(Event event) {
		if (eventRepository.save(event) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateEvent(Event event) {
		if (eventRepository.save(event) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteEvent(Long eventId) {
		if (eventRepository.findById(eventId) != null) {
			eventRepository.deleteById(eventId);
			return true;
		} else {
			return false;
		}
	}

	public Set<EventWaitingList> getEventWaitingList(Long eventId) {

		Optional<Event> opEvent = eventRepository.findById(eventId);
		if (opEvent.isPresent()) {
			return opEvent.get().getWaitingList();
		} else {
			return null;
		}

	}

	public List<Student> getStudentsWaitingByEventId(Long eventId) {
		Optional<Event> opEvent = eventRepository.findById(eventId);
		if (opEvent.isPresent()) {
			Event e = opEvent.get();

			List<Student> students = new ArrayList<Student>();

			for (EventWaitingList ew : e.getWaitingList()) {
				students.add(ew.getStudent());
			}

			return students;

		} else {
			return null;
		}
	}

	public List<Student> getStudentsByEventId(Long eventId) {
		Optional<Event> opEvent = eventRepository.findById(eventId);
		if (opEvent.isPresent()) {
			Event e = opEvent.get();

			List<Student> students = new ArrayList<Student>();

			for (EventSignUp es : e.getEventSignedUps()) {
				students.add(es.getStudent());
			}

			return students;

		} else {
			return null;
		}
	}

	public Set<Schedule> getScheduleByEventId(Long eventId) {
		Optional<Event> opEvent = eventRepository.findById(eventId);
		if (opEvent.isPresent()) {
			return opEvent.get().getEventSchedule();
		} else {
			return null;
		}
	}
}
