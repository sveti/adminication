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
@Table(name = "enrollments")
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "student_id",nullable = false)
	@JsonBackReference(value="enrollment_student")
    Student student;
	
	@ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
	@JsonBackReference(value="enrollment_course")
    Course course;
	
	@Column
	int grade;

	public Long getId() {
		return id;
	}


	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(course, grade, id, student);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enrollment other = (Enrollment) obj;
		return Objects.equals(course, other.course) && grade == other.grade && Objects.equals(id, other.id)
				&& Objects.equals(student, other.student);
	}


	@Override
	public String toString() {
		return "Enrollment [id=" + id + ", student=" + student + ", course=" + course + ", grade=" + grade + "]";
	}
	
	

	
}
