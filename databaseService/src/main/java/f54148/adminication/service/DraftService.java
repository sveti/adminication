package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Draft;
import f54148.adminication.entity.MessageStatus;
import f54148.adminication.entity.Notification;
import f54148.adminication.entity.User;
import f54148.adminication.repository.DraftRepository;

@Service
public class DraftService {
	
	private final DraftRepository draftRepository;

	private final UserService userService;
	
	public DraftService(DraftRepository draftRepository,@Lazy UserService userService) {
		super();
		this.draftRepository = draftRepository;
		this.userService = userService;
	}

	
	public List<Draft> getDrafts() {
		List<Draft> draftList = new ArrayList<>();
		draftRepository.findAll().forEach(draftList::add);
		return draftList;
	}

	public Draft getDraftById(Long draftId) {
		Optional<Draft> opDraft = draftRepository.findById(draftId);
		return opDraft.orElse(null);
	}

	public boolean addDraft(Draft draft) {

		draftRepository.save(draft);
		return true;
	}
	
	public Long addDraftAndGetId(Draft draft) {
		Draft d = draftRepository.save(draft);
		return d.getId();
	}
	
	public void updateDraft(Draft draft) {
		draftRepository.save(draft);
	}

	public boolean deleteDraft(Long draftId) {
		Optional<Draft> opDraft = draftRepository.findById(draftId);
		if (opDraft.isPresent()) {
			Draft d =  opDraft.get();
			User user = d.getSender();
			user.getDrafts().remove(d);
			userService.updateUser(user);
			
			draftRepository.deleteById(draftId);
			return true;
		} else {
			return false;
		}
	}
	
	public Long createDraftFromAdmin(String message) {
		
		Draft d = new Draft();
		d.setSender(userService.getAdmin());
		d.setContent(message);
		d.setStatus(MessageStatus.DRAFT);
		
		return addDraftAndGetId(d);
		
	}
	
	public Long createDraftFromTeacher(String message, Long teacherId) {
		
		Draft d = new Draft();
		d.setSender(userService.getUserById(teacherId));
		d.setContent(message);
		d.setStatus(MessageStatus.DRAFT);
		
		return addDraftAndGetId(d);
		
	}


	public void addNotification(Draft draft,Notification saved) {
		
		Set<Notification> draftNots = draft.getNotificationsSend();
		draftNots.add(saved);
		draft.setNotificationsSend(draftNots);
		updateDraft(draft);
		
	}

}
