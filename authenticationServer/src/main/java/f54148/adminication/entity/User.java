package f54148.adminication.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User{
	
	@Id
	private long id;
	
	@NotBlank
    @Size(min = 5, max = 50, message="Min 5, Max 50")
	@Column(unique = true)
	private String username;
	
	@NotBlank
	@Column(unique = true)
	private String email;
	
	@NotBlank
	@Size(min = 5, max = 50, message="Min 5, Max 50")
	private String password;
	
	//@Enumerated-> 1, 2, 3... -> за статус 
	//за ролята виж role 
	//many to one
	
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	//@JsonBackReference(value = "role_user")
	private Role role;

	
	
	
	

}