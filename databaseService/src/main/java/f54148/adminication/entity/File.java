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
@Table(name = "files")
public class File {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	@JsonBackReference(value = "file_teacher")
	Teacher teacher;
	
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	@JsonBackReference(value = "file_course")
	Course course;

	@Column
	private String name;

	@Column
	private String type;
	
	@Column
	private String filePath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, filePath, id, name, teacher, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		File other = (File) obj;
		return Objects.equals(course, other.course) && Objects.equals(filePath, other.filePath)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(teacher, other.teacher) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "File [id=" + id + ", teacher=" + teacher + ", course=" + course + ", name=" + name + ", type=" + type
				+ ", filePath=" + filePath + "]";
	}
	
	
	
}
