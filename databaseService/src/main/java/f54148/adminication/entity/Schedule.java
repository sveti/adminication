package f54148.adminication.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "shedules")
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "start_time", columnDefinition = "TIME")
	private LocalTime startTime;
	
	@Column(name = "end_time", columnDefinition = "TIME")
	private LocalTime endTime;
	
	@Column(name = "start_date", columnDefinition = "DATE")
	private LocalDate startDate;
	
	
	@ManyToMany(targetEntity = Course.class,fetch = FetchType.EAGER,mappedBy = "courseSchedule")
	@JsonIgnore
	List<Course> scheduledCourses = new ArrayList<>();
	
	@ManyToMany(targetEntity = Event.class,mappedBy = "eventSchedule")
	@JsonIgnore
	List<Event> scheduledEvents = new ArrayList<>();

	public Long getId() {
		return id;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}


	public List<Course> getScheduledCourses() {
		return scheduledCourses;
	}


	public void setScheduledCourses(List<Course> scheduledCourses) {
		this.scheduledCourses = scheduledCourses;
	}


	public List<Event> getScheduledEvents() {
		return scheduledEvents;
	}


	public void setScheduledEvents(List<Event> scheduledEvents) {
		this.scheduledEvents = scheduledEvents;
	}


	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endTime, id, scheduledCourses, scheduledEvents, startDate, startTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		return Objects.equals(endTime, other.endTime) && Objects.equals(id, other.id)
				&& Objects.equals(scheduledCourses, other.scheduledCourses)
				&& Objects.equals(scheduledEvents, other.scheduledEvents) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(startTime, other.startTime);
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", startDate=" + startDate
				+ ", scheduledCourses=" + scheduledCourses + ", scheduledEvents=" + scheduledEvents + "]";
	}


	
}

