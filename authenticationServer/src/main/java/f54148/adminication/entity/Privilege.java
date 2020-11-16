package f54148.adminication.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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
@Table(name = "priviledges")
public class Privilege {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank
	private String hasAccess;
	
	@ManyToMany(mappedBy = "privileges",cascade = CascadeType.ALL)
	@JsonIgnore
    private Set<Role> roles = new HashSet<Role>();

	public Privilege(@NotBlank String hasAccess) {
		super();
		this.hasAccess = hasAccess;
		this.roles = new HashSet<Role>();
	}
	
	
	
}
