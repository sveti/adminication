package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventWaitingList;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;

	
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

		 public List<EventWaitingList> getEventWaitingList(Long eventId){
			 
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
				  Event e =  opEvent.get();
				  
				  List<Student> students = new ArrayList<Student>();
				  
				  for(EventWaitingList ew : e.getWaitingList()) {
					  students.add(ew.getStudent());
				  }
				  
				  return students;
				  
			  } else {
			   return null;
			  }
		}
}
