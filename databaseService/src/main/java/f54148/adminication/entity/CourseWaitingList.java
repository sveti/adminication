package f54148.adminication.entity;

import java.time.LocalDateTime;
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
@Table(name = "course_waiting_lists")
public class CourseWaitingList  implements Comparable<CourseWaitingList>{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
	@JsonBackReference(value="coursewaitinglist_course")
    Course course;
		
	@ManyToOne
    @JoinColumn(name = "student_id",nullable = false)
	@JsonBackReference(value="coursewaitinglist_student")
    Student student;
	
	@Column(name = "signed", columnDefinition = "DATETIME")
	private LocalDateTime signed;

	public Long getId() {
		return id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public LocalDateTime getSigned() {
		return signed;
	}

	public void setSigned(LocalDateTime signed) {
		this.signed = signed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, id, signed, student);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseWaitingList other = (CourseWaitingList) obj;
		return Objects.equals(course, other.course) && Objects.equals(id, other.id)
				&& Objects.equals(signed, other.signed) && Objects.equals(student, other.student);
	}

	@Override
	public String toString() {
		return "CourseWaitingList [id=" + id + ", course=" + course + ", student=" + student + ", signed=" + signed
				+ "]";
	}

	@Override
	public int compareTo(CourseWaitingList other) {
		return this.getSigned().compareTo(other.getSigned());
	}
	
	
	
	
}
