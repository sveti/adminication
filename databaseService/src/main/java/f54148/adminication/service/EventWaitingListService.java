package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import f54148.adminication.dto.CourseReportStudentsSignedUpDTO;
import f54148.adminication.entity.EventSignUp;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.AddEventWaitingListDTO;
import f54148.adminication.dto.EventOfStudentInWaitingListDTO;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventWaitingList;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.EventWaitingListRepository;

@Service
public class EventWaitingListService {

	private final EventWaitingListRepository eventWaitingListRepository;

	private final EventService eventService;
	private final DraftService draftService;
	private final NotificationService notificationService;

	private final StudentService studentService;
	
	

	public EventWaitingListService(EventWaitingListRepository eventWaitingListRepository,@Lazy EventService eventService,
			@Lazy StudentService studentService,@Lazy DraftService draftService,@Lazy NotificationService notificationService) {
		super();
		this.eventWaitingListRepository = eventWaitingListRepository;
		this.eventService = eventService;
		this.studentService = studentService;
		this.draftService = draftService;
		this.notificationService = notificationService;
	}

	public List<EventWaitingList> getEventWaitingLists() {
		List<EventWaitingList> eventWaitingListList = new ArrayList<>();
		eventWaitingListRepository.findAll().forEach(eventWaitingListList::add);
		return eventWaitingListList;
	}

	public EventWaitingList getEventWaitingListById(Long eventWaitingListId) {
		Optional<EventWaitingList> opEventWaitingList = eventWaitingListRepository.findById(eventWaitingListId);
		return opEventWaitingList.orElse(null);
	}

	public boolean addEventWaitingList(EventWaitingList eventWaitingList) {
		eventWaitingListRepository.save(eventWaitingList);
		return true;
	}

	public boolean updateEventWaitingList(EventWaitingList eventWaitingList) {
		eventWaitingListRepository.save(eventWaitingList);
		return true;
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
	public EventWaitingList getFirstEventWaitingListInQueue(Long eventId) {
		return eventService.getEventWaitingList(eventId).stream().sorted().findFirst().get();
	}

	public Student getStudentByEventWaitingListId(Long eventWaitingListId) {
		Optional<EventWaitingList> opEventWaitingList = eventWaitingListRepository.findById(eventWaitingListId);
		return opEventWaitingList.map(EventWaitingList::getStudent).orElse(null);
	}

	public Event getEventByEventWaitingListId(Long eventWaitingListId) {
		Optional<EventWaitingList> opEventWaitingList = eventWaitingListRepository.findById(eventWaitingListId);
		return opEventWaitingList.map(EventWaitingList::getEvent).orElse(null);
	}
	
	public Integer getNumberOfStudentInQueue(EventWaitingList ew) {
		ArrayList<EventWaitingList> waitingLists = (ArrayList<EventWaitingList>)  ew.getEvent().getWaitingList().stream().sorted().collect(Collectors.toList());
		return waitingLists.indexOf(ew);
		
	}

public List<EventOfStudentInWaitingListDTO> getWaitingListsOfStudent(Long studentId){
		
		Set<EventWaitingList> eventWaitingList = studentService.getStudentById(studentId).getEventWaitingList();
		List<EventOfStudentInWaitingListDTO> dtoList = new ArrayList<>();
		
		for(EventWaitingList ew: eventWaitingList) {
			EventOfStudentInWaitingListDTO dto = new EventOfStudentInWaitingListDTO();
			dto.setEventWaitingListId(ew.getId());
			dto.setEventId(ew.getEvent().getId());
			dto.setStudentId(ew.getStudent().getId());
			dto.setNumberInLine(getNumberOfStudentInQueue(ew));
			dtoList.add(dto);
		}
		
		return dtoList;
	}

public boolean addWaitingListSignUp(AddEventWaitingListDTO dto) {
	EventWaitingList eventWaitingList = new EventWaitingList();
	eventWaitingList.setStudent(studentService.getStudentById(dto.getStudentId()));
	eventWaitingList.setEvent(eventService.getEventById(dto.getEventId()));
	eventWaitingList.setSigned(dto.getSigned());

	if(addEventWaitingList(eventWaitingList)){
		String message = eventWaitingList.getStudent().getName() + " " + eventWaitingList.getStudent().getLastName() + " has been successfully added in the waiting list for event #" + eventWaitingList.getEvent().getId() + " : " + eventWaitingList.getEvent().getTitle();
		Long draftId = draftService.createDraftFromAdmin(message);
		Long parentId = eventWaitingList.getStudent().getParent().getId();
		String studentMessage = "You been successfully been signed up for event #" + eventWaitingList.getEvent().getId() + " : " + eventWaitingList.getEvent().getTitle();
		notificationService.sendDraft(draftId, parentId);
		Long studentDraftId = draftService.createDraftFromAdmin(studentMessage);
		notificationService.sendDraft(studentDraftId, eventWaitingList.getStudent().getId());
		return true;
	}
	return false;
}

}
