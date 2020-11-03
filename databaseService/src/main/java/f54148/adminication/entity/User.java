package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private Role role;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String lastName;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonBackReference(value = "teacher")
	private Teacher teacher;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonBackReference(value = "parent")
	private Parent parent;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonBackReference(value = "student")
	private Student student;
	
	@OneToMany(mappedBy = "sender")
	@JsonManagedReference(value = "draft_sender")
	List<Draft> drafts = new ArrayList<Draft>();
	
	@OneToMany(mappedBy = "recipient")
	@JsonManagedReference(value = "notification_recipient")
	List<Notification> notificationsReceived = new ArrayList<Notification>();
	
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Draft> getDrafts() {
		return drafts;
	}
	
	
	public void setDrafts(List<Draft> drafts) {
		this.drafts = drafts;
	}
	
	

	public List<Notification> getNotificationsReceived() {
		return notificationsReceived;
	}

	public void setNotificationsReceived(List<Notification> notificationsReceived) {
		this.notificationsReceived = notificationsReceived;
	}

	@Override
	public int hashCode() {
		return Objects.hash(drafts, email, id, lastName, name, notificationsReceived, parent, password, role, student,
				teacher, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(drafts, other.drafts) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(name, other.name)
				&& Objects.equals(notificationsReceived, other.notificationsReceived)
				&& Objects.equals(parent, other.parent) && Objects.equals(password, other.password)
				&& role == other.role && Objects.equals(student, other.student)
				&& Objects.equals(teacher, other.teacher) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", role="
				+ role + ", name=" + name + ", lastName=" + lastName + ", teacher=" + (teacher==null?"null":teacher) + ", parent=" + 
				(parent==null?"null":parent)
				+ ", student=" + (student==null?"null":student) + ", drafts=" + drafts + ", notificationsReceived " + notificationsReceived + "]";
	}

	

	

}