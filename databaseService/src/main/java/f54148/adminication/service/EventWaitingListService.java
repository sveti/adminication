package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventWaitingList;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.EventWaitingListRepository;

@Service
public class EventWaitingListService {
	
	@Autowired
	private EventWaitingListRepository eventWaitingListRepository;
	
	@Autowired
	private EventService eventService;
	
	@Autowired 
	private StudentService studentService;

	
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
			
			//Questionable life choices
			return eventService.getEventWaitingList(eventId).stream().sorted().findFirst().get().getStudent();
			

		}



}
