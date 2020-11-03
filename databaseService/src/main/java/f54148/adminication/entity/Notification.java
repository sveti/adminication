package f54148.adminication.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "notifications")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "draft_id", nullable = false)
	@JsonBackReference(value = "notification_draft")
	Draft draft;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference(value = "notification_recipient")
	User recipient;
	
	@Column(name = "send", columnDefinition = "DATETIME")
	private LocalDateTime send;
	
	@Column
	String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Draft getDraft() {
		return draft;
	}

	public void setDraft(Draft draft) {
		this.draft = draft;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public LocalDateTime getSend() {
		return send;
	}

	public void setSend(LocalDateTime send) {
		this.send = send;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(draft, id, recipient, send, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		return Objects.equals(draft, other.draft) && Objects.equals(id, other.id)
				&& Objects.equals(recipient, other.recipient) && Objects.equals(send, other.send)
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", draft=" + draft + ", recipient=" + recipient + ", send=" + send
				+ ", status=" + status + "]";
	}
	
}
