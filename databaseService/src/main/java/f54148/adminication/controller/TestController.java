package f54148.adminication.controller;


import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class TestController {


	@GetMapping("/")
	public String greeting() {
		return "emerge";
	}
}
