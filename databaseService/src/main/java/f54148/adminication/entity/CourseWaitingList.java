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
@Table(name = "course_waiting_lists")
public class CourseWaitingList implements Comparable<CourseWaitingList> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	@JsonBackReference(value = "coursewaitinglist_course")
	private Course course;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonBackReference(value = "coursewaitinglist_student")
	private Student student;

	@NotNull
	@Column(name = "signed", columnDefinition = "DATETIME")
	private LocalDateTime signed;

	@Override
	public int compareTo(CourseWaitingList other) {
		return this.getSigned().compareTo(other.getSigned());
	}

}
