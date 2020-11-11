package f54148.adminication.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "parents")
@PrimaryKeyJoinColumn
public class Parent extends User {


	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@JsonManagedReference(value="parent_child")
	private Set<Student> children = new HashSet<Student>();


}
