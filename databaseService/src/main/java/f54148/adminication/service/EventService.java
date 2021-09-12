package f54148.adminication.service;

import java.util.*;

import f54148.adminication.dto.*;
import f54148.adminication.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import f54148.adminication.repository.EventRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService {
	
	
	private final EventRepository eventRepository;
	private final StudentService studentService;
	private final ScheduleService scheduleService;
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

	public List<AdminAllEventsDTO> getAllEventsAdmin() {
		List<Event> events = getEvents();

		List<AdminAllEventsDTO>  dto = new ArrayList<>();

		for(Event e: events) {
			dto.add(convertToAdminAllEventsDTO(e));
		}

		return dto;
	}

	private AdminAllEventsDTO convertToAdminAllEventsDTO(Event e) {
		return modelMapper.map(e, AdminAllEventsDTO.class);
	}

	public String addAddEventDTO(AddEventDTO event) {
		try {
			Event e = new Event();
			e.setTitle(event.getTitle());
			e.setDescription(event.getDescription());
			e.setMinAge(event.getMinAge());
			e.setMaxAge(event.getMaxAge());
			e.setMaxNumberOfPeople(event.getMaxNumberOfPeople());
			e.setStatus(CourseStatus.UPCOMMING);

			Set<Schedule> schedules = new HashSet<>();

			for(AddCourseScheduleDTO schedule: event.getSchedules()) {

				schedules.add(scheduleService.findOrCreateSchedule(schedule));
			}

			e.setEventSchedule(schedules);

			eventRepository.save(e);

			return "Event have been successfully saved!";
		}
		catch(Exception e) {
			return "An error has occurred!";
		}

	}

	public String editEvent(EditEventDTO event) {
		try {

			Event e = getEventById(event.getId());
			e.setTitle(event.getTitle());
			e.setDescription(event.getDescription());
			e.setStatus(event.getStatus());
			e.setMaxAge(event.getMaxAge());
			e.setMinAge(event.getMinAge());
			e.setMaxNumberOfPeople(event.getMaxNumberOfPeople());

			Set<Schedule> schedules = new HashSet<>();

			for(EditCourseScheduleDTO schedule: event.getSchedules()) {
				schedules.add(scheduleService.findOrCreateSchedule(schedule));
			}

			Set <Schedule> toRemove = new HashSet<>();

			for(Schedule sch : e.getEventSchedule()) {

				if(!schedules.contains(sch)) {
					toRemove.add(sch);
				}

			}

			e.setEventSchedule(schedules);


			eventRepository.save(e);

			for(Schedule sch: toRemove) {
				scheduleService.removeScheduleFromEvent(sch, e);
			}

			return "Okay";

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return "Nope";
		}
	}

	public EditEventDTO getEditEventDTO(Long idEvent) {
		Event e = getEventById(idEvent);
		EditEventDTO dto = new EditEventDTO();
		dto.setId(e.getId());
		dto.setTitle(e.getTitle());
		dto.setDescription(e.getDescription());
		dto.setStatus(e.getStatus());
		dto.setMinAge(e.getMinAge());
		dto.setMaxAge(e.getMaxAge());
		dto.setMaxNumberOfPeople(e.getMaxNumberOfPeople());

		List<EditCourseScheduleDTO> schedules = new ArrayList<>();
		for(Schedule s: e.getEventSchedule()) {
			schedules.add(modelMapper.map(s,EditCourseScheduleDTO.class));
		}

		dto.setSchedules(schedules);

		return dto;
	}
}
