package f54148.adminication.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.Event;
import f54148.adminication.entity.Schedule;
import f54148.adminication.repository.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private CourseService courseService;

	@Autowired
	private EventService eventService;

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

	public List<Course> getCoursesbyScheduleId(Long scheduleId) {
		Optional<Schedule> opSchedule = scheduleRepository.findById(scheduleId);
		if (opSchedule.isPresent()) {
			List<Course> listWithoutDuplicates = new ArrayList<>(new HashSet<>(opSchedule.get().getScheduledCourses()));
			return listWithoutDuplicates;
		} else {
			return null;
		}
	}

	public List<Event> getEventsbyScheduleId(Long scheduleId) {
		Optional<Schedule> opSchedule = scheduleRepository.findById(scheduleId);
		if (opSchedule.isPresent()) {
			List<Event> listWithoutDuplicates = new ArrayList<>(new HashSet<>(opSchedule.get().getScheduledEvents()));
			return listWithoutDuplicates;
		} else {
			return null;
		}
	}

}
