package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Draft;
import f54148.adminication.entity.User;
import f54148.adminication.repository.DraftRepository;

@Service
public class DraftService {
	
	@Autowired
	private DraftRepository draftRepository;
	
	
	@Autowired
	private UserService userService;

	public List<Draft> getDrafts() {
		List<Draft> draftList = new ArrayList<>();
		draftRepository.findAll().forEach(draftList::add);
		return draftList;
	}

	public Draft getDraftById(Long draftId) {
		Optional<Draft> opDraft = draftRepository.findById(draftId);
		if (opDraft.isPresent()) {
			return opDraft.get();
		} else {
			return null;
		}
	}

	public boolean addDraft(Draft draft) {
		if (draftRepository.save(draft) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateDraft(Draft draft) {
		if (draftRepository.save(draft) != null) {
			return true;
		} else {
			return false;
		}
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





	
	

}
