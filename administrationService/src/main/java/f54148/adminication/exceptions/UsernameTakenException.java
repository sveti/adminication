package f54148.adminication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsernameTakenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UsernameTakenException(String message) {
        super(message);
    }
}