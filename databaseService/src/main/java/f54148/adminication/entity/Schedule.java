package f54148.adminication.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "shedules")
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@DateTimeFormat(pattern = "HH:mm:ss.SSSZ")
	@Column(name = "start_time", columnDefinition = "TIME")
	private LocalTime startTime;

	@NotNull
	@DateTimeFormat(pattern = "HH:mm:ss.SSSZ")
	@Column(name = "end_time", columnDefinition = "TIME")
	private LocalTime endTime;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date", columnDefinition = "DATE")
	private LocalDate startDate;

	@ManyToMany(targetEntity = Course.class, fetch = FetchType.EAGER, mappedBy = "courseSchedule")
	@JsonIgnore
	private Set<Course> scheduledCourses = new HashSet<>();

	@ManyToMany(targetEntity = Event.class, mappedBy = "eventSchedule")
	@JsonIgnore
	private Set<Event> scheduledEvents = new HashSet<>();

	
}
