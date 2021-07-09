package f54148.adminication.dto;

import java.time.LocalDateTime;

import f54148.adminication.entity.MessageStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {
	
	private Long id;
	private String message;
	private MessageStatus status;
	private LocalDateTime send;
	private String senderName;

}
