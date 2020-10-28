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
@Table(name = "teaching")
public class Teaching {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "teacher_id",nullable = false)
	@JsonBackReference(value="teaching_teacher")
    Teacher teacher;
	
	@ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
	@JsonBackReference(value="teaching_course")
    Course course;
	
	@Column(nullable = false)
	private Double salaryPerStudent;

	public Long getId() {
		return id;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Double getSalaryPerStudent() {
		return salaryPerStudent;
	}

	public void setSalaryPerStudent(Double salaryPerStudent) {
		this.salaryPerStudent = salaryPerStudent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, id, salaryPerStudent, teacher);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teaching other = (Teaching) obj;
		return Objects.equals(course, other.course) && Objects.equals(id, other.id)
				&& Objects.equals(salaryPerStudent, other.salaryPerStudent) && Objects.equals(teacher, other.teacher);
	}

	@Override
	public String toString() {
		return "Teaching [id=" + id + ", teacher=" + teacher + ", course=" + course + ", salaryPerStudent="
				+ salaryPerStudent + "]";
	}
	
	
	
}
