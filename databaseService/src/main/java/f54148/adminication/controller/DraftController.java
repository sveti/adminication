package f54148.adminication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Draft;
import f54148.adminication.service.DraftService;

@Controller
public class DraftController {
	
	@Autowired
	private DraftService draftService;


	@PostMapping(path = "/addDraft")
	public @ResponseBody String addDraft(@RequestBody Draft draft) {
		

		if (draftService.addDraft(draft)) {
			return "Saved draft";
		} else {
			return "A problem has occured";
		}
	}
	
	@PutMapping(path = "/updateDraft")
	public @ResponseBody String updateDraft(@RequestBody Draft draft) {
		

		if (draftService.addDraft(draft)) {
			return "Updated draft";
		} else {
			return "A problem has occured";
		}
	}

	@GetMapping(path = "/drafts")
	public @ResponseBody List<Draft> getAllDrafts() {
		return draftService.getDrafts();
	}

	@GetMapping(path = "/draft/{id}")
	public @ResponseBody Draft getDraft(@PathVariable("id") Long id) {
		return draftService.getDraftById(id);
	}
	
	@DeleteMapping(path = "/draft/{id}")
	public @ResponseBody String deleteDraft(@PathVariable("id") Long id) {
		if( draftService.deleteDraft(id)) {
			return "Draft deleted!";
		}
		else {
			return "An error has occured";
		}
	}
	


}
