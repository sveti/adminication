package f54148.adminication.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
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
@Table(name = "teaching")
public class Teaching {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	@JsonBackReference(value = "teaching_teacher")
	private Teacher teacher;

	@ManyToOne
	@JoinColumn(name = "substitute_id")
	@JsonBackReference(value = "teaching_teacher_sub")
	private Teacher substitute;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	@JsonBackReference(value = "teaching_course")
	private Course course;

	@NotNull
	@DecimalMin("0.01")
	@Column
	private Double salaryPerStudent;

}
