package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "parents")
public class Parent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String lastName;
	
	@OneToMany(mappedBy="parent", cascade = CascadeType.ALL)
	@JsonManagedReference
    private Collection<Student> children = new ArrayList<Student>();

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Collection<Student> getChildren() {
		return children;
	}

	public void setChildren(Collection<Student> children) {
		this.children = children;
	}
	
	public void addChild(Student child) {
		this.children.add(child);
		child.setParent(this);
	}

	@Override
	public String toString() {
		return "Parent [id=" + id + ", user=" + user + ", name=" + name + ", lastName=" + lastName + ", children="
				+ children + "]";
	}
	
	
	

}
