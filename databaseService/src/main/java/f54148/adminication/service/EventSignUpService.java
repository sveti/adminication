package f54148.adminication.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.AddEventSignUpDTO;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventSignUp;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.EventSignUpRepository;

@Service
public class EventSignUpService {
	
	
	private final EventSignUpRepository eventSignUpRepository;

	private final EventService eventService;
	
	private final NotificationService notificationService;
	
	private final DraftService draftService;

	private final StudentService studentService;
	
	
	public EventSignUpService(EventSignUpRepository eventSignUpRepository,@Lazy EventService eventService,
			@Lazy StudentService studentService, @Lazy  NotificationService notificationService, @Lazy  DraftService draftService) {
		super();
		this.eventSignUpRepository = eventSignUpRepository;
		this.eventService = eventService;
		this.studentService = studentService;
		this.notificationService = notificationService;
		this.draftService = draftService;
	}

	public List<EventSignUp> getEventSignUps() {
		List<EventSignUp> eventSignUpList = new ArrayList<>();
		eventSignUpRepository.findAll().forEach(eventSignUpList::add);
		return eventSignUpList;
	}

	public EventSignUp getEventSignUpById(Long eventSignUpId) {
		Optional<EventSignUp> opEventSignUp = eventSignUpRepository.findById(eventSignUpId);
		return opEventSignUp.orElse(null);
	}

	public boolean addEventSignUp(EventSignUp eventSignUp) {
		eventSignUpRepository.save(eventSignUp);
		return true;
	}

	public boolean updateEventSignUp(EventSignUp eventSignUp) {
		eventSignUpRepository.save(eventSignUp);
		return true;
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
			
			Student nextS = eventService.updateEventWaitingList(e);
			
			if(nextS!=null) {
				
				EventSignUp newEsu = new EventSignUp(nextS,e);
				eventSignUpRepository.save(newEsu);
				String message = nextS.getName() + " " + nextS.getLastName() + " has been successfully enrolled in course #" + newEsu.getEvent().getId() + " : " + newEsu.getEvent().getTitle();
				Long draftId = draftService.createDraftFromAdmin(message);
				Long parentId = nextS.getParent().getId();
				String studentMessage = "You been successfully been signed up for event #" + newEsu.getEvent().getId() + " : " + newEsu.getEvent().getTitle();
				notificationService.sendDraft(draftId, parentId);
				Long studentDraftId = draftService.createDraftFromAdmin(studentMessage);
				notificationService.sendDraft(studentDraftId, nextS.getId());
				
			}

			return true;
		} else {
			return false;
		}
	}


	public boolean addEventSignUpDTO(AddEventSignUpDTO dto) {
		EventSignUp signUp = new EventSignUp();
		signUp.setStudent(studentService.getStudentById(dto.getStudentId()));
		signUp.setEvent(eventService.getEventById(dto.getEventId()));
		signUp.setSigned(LocalDateTime.now());
		if( addEventSignUp(signUp)) {
			
			String message = signUp.getStudent().getName() + " " + signUp.getStudent().getLastName() + " has been successfully enrolled in course #" + signUp.getEvent().getId() + " : " + signUp.getEvent().getTitle();
			Long draftId = draftService.createDraftFromAdmin(message);
			Long parentId = signUp.getStudent().getParent().getId();
			String studentMessage = "You been successfully been signed up for event #" + signUp.getEvent().getId() + " : " + signUp.getEvent().getTitle();
			notificationService.sendDraft(draftId, parentId);
			Long studentDraftId = draftService.createDraftFromAdmin(studentMessage);
			notificationService.sendDraft(studentDraftId, signUp.getStudent().getId());
			return true;
		}
		else {
			return false;
		}
	}

	private EventSignUp getEventSignUpByStudentAndEvent(Long studentId, Long eventId) {
		
		Student s = studentService.getStudentById(studentId);
		Event e = eventService.getEventById(eventId);

		return eventSignUpRepository.findByStudentAndEvent(s,e);
	}
	
	public boolean deleteEventSignUpByStudentIdAndEventId(Long studentId, Long eventId) {
		
		EventSignUp esu = getEventSignUpByStudentAndEvent(studentId,eventId);
		
		return deleteEventSignUp(esu.getId());
	}




}
