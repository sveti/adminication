package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
@Table(name = "teachers")
@PrimaryKeyJoinColumn
//teachers extends user
public class Teacher extends User {

	//id -> от user
	//@id + no и с fk
	
//	@Id
//	private Long id;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@MapsId
//	@JoinColumn(name = "userId", referencedColumnName = "id")
//	@JsonManagedReference(value = "teacher")
//	private User user;

	@OneToMany(mappedBy = "teacher")
	@JsonManagedReference(value = "teaching_teacher")
	private Set<Teaching> teaching = new HashSet<Teaching>();

	@OneToMany(mappedBy = "substitute")
	@JsonManagedReference(value = "teaching_teacher_sub")
	private Set<Teaching> substituting = new HashSet<Teaching>();

	@OneToMany(mappedBy = "teacher")
	@JsonManagedReference(value = "lesson_teacher")
	private Set<Lesson> lessons = new HashSet<Lesson>();
	
	@OneToMany(mappedBy = "teacher")
	@JsonManagedReference(value = "file_teacher")
	private Set<File> files = new HashSet<File>();

	

	

}
