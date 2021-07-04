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
@Table(name = "event_sign_ups")
public class EventSignUp {
	


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	@JsonBackReference(value = "event_sign_up_event")
	private Event event;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonBackReference(value = "event_sign_up_student")
	private Student student;

	@NotNull
	@Column(name = "signed", columnDefinition = "DATETIME")
	private LocalDateTime signed;
	
	public EventSignUp(Student s, Event e) {
		super();
		this.student = s;
		this.event = e;
		this.signed = LocalDateTime.now();
	}
	
}
