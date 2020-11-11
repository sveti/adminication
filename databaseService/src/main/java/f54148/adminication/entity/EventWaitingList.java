package f54148.adminication.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "event_waiting_lists")
public class EventWaitingList implements Comparable<EventWaitingList> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	@JsonBackReference(value = "eventwaitinglist_event")
	private Event event;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonBackReference(value = "eventwaitinglist_student")
	private Student student;

	@NotNull
	@Column(name = "signed", columnDefinition = "DATETIME")
	private LocalDateTime signed;


	@Override
	public int compareTo(EventWaitingList other) {
		return this.getSigned().compareTo(other.getSigned());
	}

}
