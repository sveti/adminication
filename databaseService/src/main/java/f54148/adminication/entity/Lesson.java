package f54148.adminication.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "lessons")
public class Lesson {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	@JsonBackReference(value = "lesson_course")
	private Course course;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	@JsonBackReference(value = "lesson_teacher")
	private Teacher teacher;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date", columnDefinition = "DATE")
	private LocalDate date;

	@Lob
	@Column(name = "description", length = 1023)
	private String description;
	
	@OneToMany(mappedBy = "lesson")
	@JsonManagedReference(value = "attendance_lesson")
	private Set<Attendance> attendances = new HashSet<Attendance>();
	

}
