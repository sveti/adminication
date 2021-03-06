package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.Schedule;
import f54148.adminication.repository.ScheduleRepository;

@Service
public class ScheduleService {


	private final ScheduleRepository scheduleRepository;

	private final CourseService courseService;

	private final EventService eventService;
	
	

	public ScheduleService(ScheduleRepository scheduleRepository, @Lazy CourseService courseService,
			@Lazy EventService eventService) {
		super();
		this.scheduleRepository = scheduleRepository;
		this.courseService = courseService;
		this.eventService = eventService;
	}

	public List<Schedule> getSchedules() {
		List<Schedule> scheduleList = new ArrayList<>();
		scheduleRepository.findAll().forEach(scheduleList::add);
		return scheduleList;
	}

	public Schedule getScheduleById(Long scheduleId) {
		Optional<Schedule> opSchedule = scheduleRepository.findById(scheduleId);
		if (opSchedule.isPresent()) {
			return opSchedule.get();
		} else {
			return null;
		}
	}

	public boolean addSchedule(Schedule schedule) {
		if (scheduleRepository.save(schedule) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateSchedule(Schedule schedule) {
		if (scheduleRepository.save(schedule) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteSchedule(Long scheduleId) {
		Optional<Schedule> opSchedule = scheduleRepository.findById(scheduleId);
		if (opSchedule.isPresent()) {

			Schedule schedule = opSchedule.get();

			for (Course c : schedule.getScheduledCourses()) {
				c.getCourseSchedule().remove(schedule);
				courseService.updateCourse(c);
			}

			for (Event e : schedule.getScheduledEvents()) {
				e.getEventSchedule().remove(schedule);
				eventService.updateEvent(e);
			}
			scheduleRepository.deleteById(scheduleId);
			return true;

		} else {
			return false;
		}
	}

	public Set<Course> getCoursesbyScheduleId(Long scheduleId) {
		Optional<Schedule> opSchedule = scheduleRepository.findById(scheduleId);
		if (opSchedule.isPresent()) {
			return opSchedule.get().getScheduledCourses();
		} else {
			return null;
		}
	}

	public Set<Event> getEventsbyScheduleId(Long scheduleId) {
		Optional<Schedule> opSchedule = scheduleRepository.findById(scheduleId);
		if (opSchedule.isPresent()) {
			return opSchedule.get().getScheduledEvents();
		} else {
			return null;
		}
	}

}
