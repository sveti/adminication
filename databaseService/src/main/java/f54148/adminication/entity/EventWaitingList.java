package f54148.adminication.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "event_waiting_lists")
public class EventWaitingList implements Comparable<EventWaitingList> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	@JsonBackReference(value = "eventwaitinglist_event")
	Event event;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonBackReference(value = "eventwaitinglist_student")
	Student student;

	@Column(name = "signed", columnDefinition = "DATETIME")
	private LocalDateTime signed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public LocalDateTime getSigned() {
		return signed;
	}

	public void setSigned(LocalDateTime signed) {
		this.signed = signed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(event, id, signed, student);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventWaitingList other = (EventWaitingList) obj;
		return Objects.equals(event, other.event) && Objects.equals(id, other.id)
				&& Objects.equals(signed, other.signed) && Objects.equals(student, other.student);
	}

	@Override
	public String toString() {
		return "EventWaitingList [id=" + id + ", event=" + event + ", student=" + student + ", signed=" + signed + "]";
	}

	@Override
	public int compareTo(EventWaitingList other) {
		return this.getSigned().compareTo(other.getSigned());
	}

}
