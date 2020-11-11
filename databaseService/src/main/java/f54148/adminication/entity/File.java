package f54148.adminication.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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
@Table(name = "files")
public class File {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	@JsonBackReference(value = "file_teacher")
	private Teacher teacher;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	@JsonBackReference(value = "file_course")
	private Course course;

	@NotBlank
	@Column
	private String name;

	@NotBlank
	@Column
	private String type;
	
	@NotBlank
	@Column
	private String filePath;	
	
}
