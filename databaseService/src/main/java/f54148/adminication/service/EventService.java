package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.EventDTO;
import f54148.adminication.entity.CourseStatus;
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
	private final StudentService studentService;
	private final EventWaitingListService eventWaitingListService;
	private final ModelMapper modelMapper;

	public List<Event> getEvents() {
		List<Event> eventList = new ArrayList<>();
		eventRepository.findAll().forEach(eventList::add);
		return eventList;
	}

	public Event getEventById(Long eventId) {
		Optional<Event> opEvent = eventRepository.findById(eventId);
		return opEvent.orElse(null);
	}

	public boolean addEvent(Event event) {
		eventRepository.save(event);
		return true;
	}

	public void updateEvent(Event event) {
		eventRepository.save(event);
	}

	public boolean deleteEvent(Long eventId) {
		if (eventRepository.findById(eventId).isPresent()) {
			eventRepository.deleteById(eventId);
			return true;
		} else {
			return false;
		}
	}

	public Set<EventWaitingList> getEventWaitingList(Long eventId) {

		Optional<Event> opEvent = eventRepository.findById(eventId);
		return opEvent.map(Event::getWaitingList).orElse(null);

	}

	public List<Student> getStudentsWaitingByEventId(Long eventId) {
		Optional<Event> opEvent = eventRepository.findById(eventId);
		if (opEvent.isPresent()) {
			Event e = opEvent.get();

			List<Student> students = new ArrayList<>();

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

			List<Student> students = new ArrayList<>();

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
		return opEvent.map(Event::getEventSchedule).orElse(null);
	}
	
	public EventDTO convertToEventDTO(Event e) {
		return modelMapper.map(e,EventDTO.class);
	}
	
	
	public List<EventDTO> getAllEvents(){
		
		List<Event> allEvents = getEvents();
		allEvents.removeIf(event -> event.getStatus() == CourseStatus.FINISHED || event.getStatus() == CourseStatus.CANCELED);
		
		List<EventDTO> dtoList = new ArrayList<>();
		
		for(Event e: allEvents) {
			dtoList.add(convertToEventDTO(e));
		}
	
		return dtoList;
		
	}

	public List<EventDTO> getEventsOfStudent(Long studentId) {
		
		List<Event> allEvents = studentService.getEventsStudentById(studentId);
		
		List<EventDTO> dtoList = new ArrayList<>();
		
		for(Event e: allEvents) {
			dtoList.add(convertToEventDTO(e));
		}
	
		return dtoList;
		
	}

	public Student updateEventWaitingList(Event e) {
		
		if(e.getWaitingList().size()==0)return null;
		
		EventWaitingList esu = eventWaitingListService.getFirstEventWaitingListInQueue(e.getId());
		
		Student s = esu.getStudent();
		
		eventWaitingListService.deleteEventWaitingList(esu.getId());
		
		return s;
	}
}
