package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonManagedReference(value="parent")
	private User user;
	
	@OneToMany(mappedBy="parent", cascade = CascadeType.MERGE)
	@JsonManagedReference
    private Collection<Student> children = new ArrayList<Student>();

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		String result =  "Parent [id=" + id + ", user=" + user + ", children=" ;
		
		for(Student child: children) {
			result+= child.getId() + " " + child.getUser().getName() + " " + child.getUser().getLastName() + "|";
		}
		
		result+="]";
		
		return result;
	}

	

}
