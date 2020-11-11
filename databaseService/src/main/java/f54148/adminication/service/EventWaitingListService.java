package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventWaitingList;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.EventWaitingListRepository;

@Service
public class EventWaitingListService {

	private final EventWaitingListRepository eventWaitingListRepository;

	private final EventService eventService;

	private final StudentService studentService;
	
	

	public EventWaitingListService(EventWaitingListRepository eventWaitingListRepository,@Lazy EventService eventService,
			@Lazy StudentService studentService) {
		super();
		this.eventWaitingListRepository = eventWaitingListRepository;
		this.eventService = eventService;
		this.studentService = studentService;
	}

	public List<EventWaitingList> getEventWaitingLists() {
		List<EventWaitingList> eventWaitingListList = new ArrayList<>();
		eventWaitingListRepository.findAll().forEach(eventWaitingListList::add);
		return eventWaitingListList;
	}

	public EventWaitingList getEventWaitingListById(Long eventWaitingListId) {
		Optional<EventWaitingList> opEventWaitingList = eventWaitingListRepository.findById(eventWaitingListId);
		if (opEventWaitingList.isPresent()) {
			return opEventWaitingList.get();
		} else {
			return null;
		}
	}

	public boolean addEventWaitingList(EventWaitingList eventWaitingList) {
		if (eventWaitingListRepository.save(eventWaitingList) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateEventWaitingList(EventWaitingList eventWaitingList) {
		if (eventWaitingListRepository.save(eventWaitingList) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteEventWaitingList(Long eventWaitingListId) {
		Optional<EventWaitingList> opEventWaitingList = eventWaitingListRepository.findById(eventWaitingListId);
		if (opEventWaitingList.isPresent()) {
			EventWaitingList ew = opEventWaitingList.get();

			Student s = ew.getStudent();
			s.getEventWaitingList().remove(ew);
			studentService.updateStudent(s);

			Event e = ew.getEvent();
			e.getWaitingList().remove(ew);
			eventService.updateEvent(e);

			eventWaitingListRepository.deleteById(eventWaitingListId);

			return true;
		} else {
			return false;
		}
	}

	public Student getFirstStudentInQueue(Long eventId) {

		return eventService.getEventWaitingList(eventId).stream().sorted().findFirst().get().getStudent();

	}

	public Student getStudentByEventWaitingListId(Long eventWaitingListId) {
		Optional<EventWaitingList> opEventWaitingList = eventWaitingListRepository.findById(eventWaitingListId);
		if (opEventWaitingList.isPresent()) {
			return opEventWaitingList.get().getStudent();
		} else {
			return null;
		}
	}

	public Event getEventByEventWaitingListId(Long eventWaitingListId) {
		Optional<EventWaitingList> opEventWaitingList = eventWaitingListRepository.findById(eventWaitingListId);
		if (opEventWaitingList.isPresent()) {
			return opEventWaitingList.get().getEvent();
		} else {
			return null;
		}
	}

}
