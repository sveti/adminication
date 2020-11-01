package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "teachers")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonManagedReference(value = "teacher")
	private User user;

	@OneToMany(mappedBy = "teacher")
	@JsonManagedReference(value = "teaching_teacher")
	List<Teaching> teaching = new ArrayList<Teaching>();

	@OneToMany(mappedBy = "substitute")
	@JsonManagedReference(value = "teaching_teacher_sub")
	List<Teaching> substituting = new ArrayList<Teaching>();

	@OneToMany(mappedBy = "teacher")
	@JsonManagedReference(value = "lesson_teacher")
	List<Lesson> lessons = new ArrayList<Lesson>();

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Teaching> getTeaching() {
		return teaching;
	}

	public void setTeaching(List<Teaching> teaching) {
		this.teaching = teaching;
	}

	public List<Teaching> getSubstituting() {
		return substituting;
	}

	public void setSubstituting(List<Teaching> substituting) {
		this.substituting = substituting;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, lessons, substituting, teaching, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		return Objects.equals(id, other.id) && Objects.equals(lessons, other.lessons)
				&& Objects.equals(substituting, other.substituting) && Objects.equals(teaching, other.teaching)
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", user=" + user + ", teaching=" + teaching + ", substituting=" + substituting
				+ ", lessons=" + lessons + "]";
	}

}
