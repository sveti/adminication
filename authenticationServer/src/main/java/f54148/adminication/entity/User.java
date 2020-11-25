package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements UserDetails {
	
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
	
	@Column(columnDefinition="tinyint(1) default 1")
    private boolean isAccountNonExpired;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean isAccountNonLocked;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean isCredentialsNonExpired;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean isEnabled;
	
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Arrays.stream(this.getRole().getName().split(","))
		.map(SimpleGrantedAuthority::new)
		.collect(Collectors.toList());
		
	}

	public List<String> getEndpoints() {
		
		List<String> endpoints = new ArrayList<String>();
		for(Privilege p : this.role.getPrivileges()) {
			endpoints.add(p.getHasAccess());
		}
		
		return endpoints;
		
	}

	
}