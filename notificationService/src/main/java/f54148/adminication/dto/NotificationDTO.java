package f54148.adminication.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {
	
	private Long id;
	private String message;
	private String status;
	private LocalDateTime send;
	private String senderName;

}
