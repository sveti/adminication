package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
@Table(name = "users")
public class User extends BaseEntity{
	
	@NotBlank
    @Size(min = 5, max = 50, message="Min 5, Max 50")
	@Column(unique = true)
	private String username;
	
	@NotBlank
	@Column(unique = true)
	private String email;
	
	@NotBlank
	@Size(min = 5, message="Min 5")
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

	@NotBlank
    @Size(min = 1, max = 50, message="Min 1, Max 50")
	private String name;

	@NotBlank
    @Size(min = 1, max = 50, message="Min 1, Max 50")
	private String lastName;
	
    @Enumerated(EnumType.ORDINAL)
	private Gender gender;
	
	@OneToMany(mappedBy = "sender")
	@JsonManagedReference(value = "draft_sender")
	private List<Draft> drafts = new ArrayList<Draft>();
	
	@OneToMany(mappedBy = "recipient")
	@JsonManagedReference(value = "notification_recipient")
	private List<Notification> notificationsReceived = new ArrayList<Notification>();
	
	
	

}