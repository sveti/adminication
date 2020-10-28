package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonManagedReference(value="student")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonBackReference
	private Parent parent;
	
	@OneToMany(mappedBy = "student")
	@JsonManagedReference(value="enrollment_student")
    List<Enrollment> enrollments = new ArrayList<Enrollment>();

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Parent getParent() {
		return parent;	
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", user=" + user + ", parent=" + parent.getId()+ parent.getUser().getName() + " " + parent.getUser().getLastName() + "]";
	}

	
	
}
