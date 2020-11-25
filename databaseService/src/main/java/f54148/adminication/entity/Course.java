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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
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
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column
	private String title;

	@NotNull
	@DecimalMin("0.01")
	@Column
	private Double pricePerStudent;


	@NotNull
    @Min(1)
	@Column
	private Integer maxStudents;

    @Enumerated(EnumType.ORDINAL)
    private CourseStatus status;

    @Enumerated(EnumType.ORDINAL)
	private Level level;

    @NotNull
	@Column
	private Integer duration;
	

	@ManyToMany(targetEntity = CourseDetail.class, cascade = CascadeType.ALL)
	@JoinTable(name = "course_has_coursedetails", joinColumns = {
			@JoinColumn(name = "course_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "course_details_id", referencedColumnName = "id") })
	private Set<CourseDetail> details = new HashSet<>();

	@OneToMany(mappedBy = "course")
	@JsonManagedReference(value = "enrollment_course")
	private Set<Enrollment> enrollments = new HashSet<Enrollment>();

	@OneToMany(mappedBy = "course")
	@JsonManagedReference(value = "teaching_course")
	private Set<Teaching> teaching = new HashSet<Teaching>();

	@ManyToMany(targetEntity = Schedule.class, cascade = CascadeType.ALL)
	@JoinTable(name = "course_has_schedule", joinColumns = {
			@JoinColumn(name = "course_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "schedule_id", referencedColumnName = "id") }, uniqueConstraints = @UniqueConstraint(columnNames = {
							"course_id", "schedule_id" }))
	private Set<Schedule> courseSchedule = new HashSet<>();

	@OneToMany(mappedBy = "course")
	@JsonManagedReference(value = "coursewaitinglist_course")
	private Set<CourseWaitingList> courseWaitingList = new HashSet<CourseWaitingList>();

	@OneToMany(mappedBy = "course")
	@JsonManagedReference(value = "lesson_course")
	private Set<Lesson> lessons = new HashSet<Lesson>();
	
	@OneToMany(mappedBy = "course")
	@JsonManagedReference(value = "file_course")
	private Set<File> files = new HashSet<File>();

	

}
