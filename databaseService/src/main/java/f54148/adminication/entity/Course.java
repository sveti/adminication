package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	private Integer maxStudents;
	
	@Column(nullable = false)
	private String status;
	
	@Column(nullable = false)
	private Level level;
	
	@Column(nullable = false)
	private Integer duration;
	
	@ManyToMany(targetEntity = CourseDetails.class,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "course_has_coursedetails",
            joinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "course_details_id", referencedColumnName = "id")})
	 List<CourseDetails> details = new ArrayList<>();
	
	 @OneToMany(mappedBy = "course")
	 @JsonManagedReference(value="enrollment_course")
	 List<Enrollment> enrollments = new ArrayList<Enrollment>();

	 @OneToMany(mappedBy = "course")
	 @JsonManagedReference(value="teaching_course")
	 List<Teaching> teaching = new ArrayList<Teaching>();
	 
	 @ManyToMany(targetEntity = Schedule.class, cascade = CascadeType.ALL)
	    @JoinTable(name = "course_has_schedule",
	            joinColumns = {
	                    @JoinColumn(name = "course_id", referencedColumnName = "id")},
	            inverseJoinColumns = {
	                    @JoinColumn(name = "schedule_id", referencedColumnName = "id")},
	            uniqueConstraints = @UniqueConstraint(columnNames = {
                        "course_id", "schedule_id" }))
	 List<Schedule> courseSchedule = new ArrayList<>();
	 
	 
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

	public List<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	public List<Teaching> getTeaching() {
		return teaching;
	}

	public void setTeaching(List<Teaching> teaching) {
		this.teaching = teaching;
	}

	public List<Schedule> getCourseSchedule() {
		return courseSchedule;
	}

	public void setCourseSchedule(List<Schedule> courseSchedule) {
		this.courseSchedule = courseSchedule;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseSchedule, details, duration, enrollments, id, level, maxStudents, pricePerStudent,
				status, teaching, title);
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
		return Objects.equals(courseSchedule, other.courseSchedule) && Objects.equals(details, other.details)
				&& Objects.equals(duration, other.duration) && Objects.equals(enrollments, other.enrollments)
				&& Objects.equals(id, other.id) && level == other.level
				&& Objects.equals(maxStudents, other.maxStudents)
				&& Objects.equals(pricePerStudent, other.pricePerStudent) && Objects.equals(status, other.status)
				&& Objects.equals(teaching, other.teaching) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + ", pricePerStudent=" + pricePerStudent + ", maxStudents="
				+ maxStudents + ", status=" + status + ", level=" + level + ", duration=" + duration + ", details="
				+ details + ", enrollments=" + enrollments + ", teaching=" + teaching + ", courseSchedule="
				+ courseSchedule + "]";
	}

	
	
	
	

}
