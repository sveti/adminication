package f54148.adminication.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {
	
	@Value("${my.greeting:}")
	public String greetingMessage;

	
	@GetMapping("/")
	public String greeting() {
		return greetingMessage;
	}
}
