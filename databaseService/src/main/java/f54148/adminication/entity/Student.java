package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonManagedReference(value="student")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonBackReference
	private Parent parent;
	
	@OneToMany(mappedBy = "student")
	@JsonManagedReference(value="enrollment_student")
    List<Enrollment> enrollments = new ArrayList<Enrollment>();
	
	@ManyToMany(targetEntity = Event.class,mappedBy = "eventStudents")
	@JsonIgnore
	List<Event> events = new ArrayList<>();
	
	@OneToMany(mappedBy = "student")
	@JsonManagedReference(value="eventwaitinglist_student")
    List<EventWaitingList> eventWaitingList = new ArrayList<EventWaitingList>();
	
	@OneToMany(mappedBy = "student")
	@JsonManagedReference(value="coursewaitinglist_student")
    List<CourseWaitingList> courseWaitingList = new ArrayList<CourseWaitingList>();
	
	

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Parent getParent() {
		return parent;	
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	public List<EventWaitingList> getEventWaitingList() {
		return eventWaitingList;
	}

	public void setEventWaitingList(List<EventWaitingList> eventWaitingList) {
		this.eventWaitingList = eventWaitingList;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<CourseWaitingList> getCourseWaitingList() {
		return courseWaitingList;
	}

	public void setCourseWaitingList(List<CourseWaitingList> courseWaitingList) {
		this.courseWaitingList = courseWaitingList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseWaitingList, enrollments, eventWaitingList, events, id, parent, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(courseWaitingList, other.courseWaitingList)
				&& Objects.equals(enrollments, other.enrollments)
				&& Objects.equals(eventWaitingList, other.eventWaitingList) && Objects.equals(events, other.events)
				&& Objects.equals(id, other.id) && Objects.equals(parent, other.parent)
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", user=" + user + ", parent=" + parent + ", enrollments=" + enrollments
				+ ", events=" + events + ", eventWaitingList=" + eventWaitingList + ", courseWaitingList="
				+ courseWaitingList + "]";
	}

	
	

	
	
}
