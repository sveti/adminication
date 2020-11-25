package f54148.adminication.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "drafts")
public class Draft {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Lob
	@Column(name = "content", length = 2047)
	private String content;
	
	@Enumerated(EnumType.ORDINAL)
	private MessageStatus status;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	@JsonBackReference(value = "draft_sender")
	private User sender;
	
	@OneToMany(mappedBy = "draft")
	@JsonManagedReference(value = "notification_draft")
	private Set<Notification> notificationsSend = new HashSet<Notification>();
	
	

}
