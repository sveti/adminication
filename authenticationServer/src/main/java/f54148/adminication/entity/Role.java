package f54148.adminication.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank
	@Size(min = 5, max = 50, message="Min 5, Max 50")
	private String name;
	
	@ManyToMany
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges = new HashSet<Privilege>();
	
	@OneToMany(mappedBy = "role")
	@JsonIgnore
	private Set<User> users  = new HashSet<User>();

	public Role(@NotBlank @Size(min = 5, max = 50, message = "Min 5, Max 50") String name) {
		super();
		this.name = name;
		this. privileges = new HashSet<Privilege>();
		this.users  = new HashSet<User>();
	}
	
	
	
	
}
//за базата
//role -> id, име, 1 Role -> Many Priviledges
//one to many -> User
//Priviledges -> до кой домейн има достъп