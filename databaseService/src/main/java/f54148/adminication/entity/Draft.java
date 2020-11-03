package f54148.adminication.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "drafts")
public class Draft {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Lob
	@Column(name = "content", length = 2047)
	private String content;
	
	@Column
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	@JsonBackReference(value = "draft_sender")
	User sender;
	
	@OneToMany(mappedBy = "draft")
	@JsonManagedReference(value = "notification_draft")
	List<Notification> notificationsSend = new ArrayList<Notification>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public List<Notification> getNotificationsSend() {
		return notificationsSend;
	}

	public void setNotificationsSend(List<Notification> notificationsSend) {
		this.notificationsSend = notificationsSend;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, id, notificationsSend, sender, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Draft other = (Draft) obj;
		return Objects.equals(content, other.content) && Objects.equals(id, other.id)
				&& Objects.equals(notificationsSend, other.notificationsSend) && Objects.equals(sender, other.sender)
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "Draft [id=" + id + ", content=" + content + ", status=" + status + ", sender=" + sender
				+ ", notificationsSend=" + notificationsSend + "]";
	}

	

	
	

}
