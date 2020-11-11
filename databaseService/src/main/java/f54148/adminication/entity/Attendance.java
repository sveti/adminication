package f54148.adminication.entity;

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
@Table(name = "attendances")
public class Attendance {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonBackReference(value = "attendance_student")
	private Student student;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "lesson_id", nullable = false)
	@JsonBackReference(value = "attendance_lesson")
	private Lesson lesson;
	
	@NotNull
	@Column(columnDefinition="tinyint(1) default 1")
	private boolean attended;

	

}
