package f54148.adminication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import f54148.adminication.exceptions.EmailTakenException;
import f54148.adminication.exceptions.UsernameTakenException;

@ControllerAdvice
public class ExceptionsHandler {

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<String> handleException(Exception exception) {
//        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
//    }
    
    @ExceptionHandler(UsernameTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<String> handleUsernameException(UsernameTakenException exception) {
    	System.out.println("===gotcha===");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(EmailTakenException.class)
    protected ResponseEntity<String> handleEmailException(EmailTakenException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}