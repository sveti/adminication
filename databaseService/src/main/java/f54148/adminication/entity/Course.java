package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Double pricePerStudent;
	
	@Column(nullable = false)
	private Double salaryPerChild;
	
	@Column(nullable = false)
	private Integer maxStudents;
	
	@Column(nullable = false)
	private String status;
	
	@Column(nullable = false)
	private Level level;
	
	@Column(nullable = false)
	private Integer duration;
	
	@ManyToMany(targetEntity = CourseDetails.class,fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "course_has_coursedetails",
            joinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "course_details_id", referencedColumnName = "id")})
	 List<CourseDetails> details = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPricePerStudent() {
		return pricePerStudent;
	}

	public void setPricePerStudent(Double pricePerStudent) {
		this.pricePerStudent = pricePerStudent;
	}

	public Double getSalaryPerChild() {
		return salaryPerChild;
	}

	public void setSalaryPerChild(Double salaryPerChild) {
		this.salaryPerChild = salaryPerChild;
	}

	public Integer getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(Integer maxStudents) {
		this.maxStudents = maxStudents;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
	public List<CourseDetails> getDetails() {
		return details;
	}

	public void setDetails(List<CourseDetails> details) {
		this.details = details;
	}

	public void addDetail(CourseDetails detail) {
		this.details.add(detail);
		detail.courses.add(this);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(details, duration, id, level, maxStudents, pricePerStudent, salaryPerChild, status, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(details, other.details) && Objects.equals(duration, other.duration)
				&& Objects.equals(id, other.id) && level == other.level
				&& Objects.equals(maxStudents, other.maxStudents)
				&& Objects.equals(pricePerStudent, other.pricePerStudent)
				&& Objects.equals(salaryPerChild, other.salaryPerChild) && Objects.equals(status, other.status)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + ", pricePerStudent=" + pricePerStudent + ", salaryPerChild="
				+ salaryPerChild + ", maxStudents=" + maxStudents + ", status=" + status + ", level=" + level
				+ ", duration=" + duration + ", details=" + details + "]";
	}

	

}
