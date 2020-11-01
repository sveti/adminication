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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "attendances")
public class Attendance {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonBackReference(value = "attendance_student")
	Student student;
	
	@ManyToOne
	@JoinColumn(name = "lesson_id", nullable = false)
	@JsonBackReference(value = "attendance_lesson")
	Lesson lesson;
	
	@Column(columnDefinition="tinyint(1) default 1")
	private boolean attended;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public boolean isAttended() {
		return attended;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attended, id, lesson, student);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attendance other = (Attendance) obj;
		return attended == other.attended && Objects.equals(id, other.id) && Objects.equals(lesson, other.lesson)
				&& Objects.equals(student, other.student);
	}

	@Override
	public String toString() {
		return "Attendance [id=" + id + ", student=" + student + ", lesson=" + lesson + ", attended=" + attended + "]";
	}
	
	
	
	

}
