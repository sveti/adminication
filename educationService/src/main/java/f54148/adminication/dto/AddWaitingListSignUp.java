package f54148.adminication.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddWaitingListSignUp {
	private long studentId;
	private long courseId;
	private LocalDateTime signed;
}
