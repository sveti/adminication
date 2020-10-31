package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "events")
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String title;
		
	@Column
	private Integer minAge;
	
	@Column
	private Integer maxAge;
	
	@Column(nullable = false)
	private Integer maxNumberOfPeople;
	
	@Column(nullable = false)
	private String status;
	
	@Lob 
	@Column(name="description", length=1023)
	private String description;
	
	@ManyToMany(targetEntity = Schedule.class, cascade = CascadeType.ALL)
    @JoinTable(name = "event_has_schedule",
            joinColumns = {
                    @JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "schedule_id", referencedColumnName = "id")},
            uniqueConstraints = @UniqueConstraint(columnNames = {
                    "event_id", "schedule_id" }))
	List<Schedule> eventSchedule = new ArrayList<>();
	
	@ManyToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    @JoinTable(name = "event_has_students",
            joinColumns = {
                    @JoinColumn(name = "event_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "student_id", referencedColumnName = "id")})
	List<Student> eventStudents = new ArrayList<>();
	
	@OneToMany(mappedBy = "event")
	@JsonManagedReference(value="eventwaitinglist_event")
    List<EventWaitingList> waitingList = new ArrayList<EventWaitingList>();
	
	
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public Integer getMaxNumberOfPeople() {
		return maxNumberOfPeople;
	}

	public void setMaxNumberOfPeople(Integer maxNumberOfPeople) {
		this.maxNumberOfPeople = maxNumberOfPeople;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Schedule> getEventSchedule() {
		return eventSchedule;
	}

	public void setEventSchedule(List<Schedule> eventSchedule) {
		this.eventSchedule = eventSchedule;
	}

	public List<EventWaitingList> getWaitingList() {
		return waitingList;
	}

	public void setWaitingList(List<EventWaitingList> waitingList) {
		this.waitingList = waitingList;
	}

	public List<Student> getEventStudents() {
		return eventStudents;
	}

	public void setEventStudents(List<Student> eventStudents) {
		this.eventStudents = eventStudents;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, eventSchedule, eventStudents, id, maxAge, maxNumberOfPeople, minAge, status,
				title, waitingList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(description, other.description) && Objects.equals(eventSchedule, other.eventSchedule)
				&& Objects.equals(eventStudents, other.eventStudents) && Objects.equals(id, other.id)
				&& Objects.equals(maxAge, other.maxAge) && Objects.equals(maxNumberOfPeople, other.maxNumberOfPeople)
				&& Objects.equals(minAge, other.minAge) && Objects.equals(status, other.status)
				&& Objects.equals(title, other.title) && Objects.equals(waitingList, other.waitingList);
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", minAge=" + minAge + ", maxAge=" + maxAge
				+ ", maxNumberOfPeople=" + maxNumberOfPeople + ", status=" + status + ", description=" + description
				+ ", eventSchedule=" + eventSchedule + ", eventStudents=" + eventStudents + ", waitingList="
				+ waitingList + "]";
	}

	
	
	
	


}
