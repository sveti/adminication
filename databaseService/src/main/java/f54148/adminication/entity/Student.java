package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "students")
@PrimaryKeyJoinColumn
public class Student extends User {

	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonBackReference(value="parent_child")
	private Parent parent;

	@OneToMany(mappedBy = "student")
	@JsonManagedReference(value = "enrollment_student")
	private Set<Enrollment> enrollments = new HashSet<Enrollment>();
	
	@OneToMany(mappedBy = "student")
	@JsonManagedReference(value = "coursewaitinglist_student")
	private Set<CourseWaitingList> courseWaitingList = new HashSet<CourseWaitingList>();

	@OneToMany(mappedBy = "student")
	@JsonManagedReference(value = "event_sign_up_student")
	private Set<EventSignUp> eventsSignedUp = new HashSet<EventSignUp>();

	@OneToMany(mappedBy = "student")
	@JsonManagedReference(value = "eventwaitinglist_student")
	private Set<EventWaitingList> eventWaitingList = new HashSet<EventWaitingList>();
	
	@OneToMany(mappedBy = "student")
	@JsonManagedReference(value = "attendance_student")
	private Set<Attendance> attendances = new HashSet<Attendance>();

	
	
}
