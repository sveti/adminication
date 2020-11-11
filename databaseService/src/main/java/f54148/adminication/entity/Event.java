package f54148.adminication.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column
	private String title;

	@NotNull
    @Min(1)
	@Column
	private Integer minAge;

	@NotNull
	@Column
	private Integer maxAge;

	@NotNull
    @Min(1)
	@Column
	private Integer maxNumberOfPeople;
	
	@Enumerated(EnumType.ORDINAL)
	private CourseStatus status;

	@NotNull
	@Lob
	@Column(name = "description", length = 1023)
	private String description;

	@ManyToMany(targetEntity = Schedule.class, cascade = CascadeType.ALL)
	@JoinTable(name = "event_has_schedule", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "schedule_id", referencedColumnName = "id") }, uniqueConstraints = @UniqueConstraint(columnNames = {
							"event_id", "schedule_id" }))
	private Set<Schedule> eventSchedule = new HashSet<>();

	@OneToMany(mappedBy = "event")
	@JsonManagedReference(value = "event_sign_up_event")
	private Set<EventSignUp> eventSignedUps = new HashSet<>();

	@OneToMany(mappedBy = "event")
	@JsonManagedReference(value = "eventwaitinglist_event")
	private Set<EventWaitingList> waitingList = new HashSet<EventWaitingList>();



}
