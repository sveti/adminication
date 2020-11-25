package f54148.adminication.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.User;
import f54148.adminication.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class TestController {
	
	private final UserService userservice;

	@GetMapping(path = "/hi")
	public @ResponseBody String greeting() {
		return "hi";
	}

	@GetMapping(path = "/")
	public @ResponseBody String home(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        String info = principal.getUsername();
		
		return ("<h1>Welcome ," + info + "<br>" + principal.getEndpoints() + "</h1>");
	}

	@GetMapping(path = "/user")
	public @ResponseBody String user() {
		return ("<h1>Welcome User</h1>");
	}

	@GetMapping(path = "/admin")
	public @ResponseBody String admin() {
		return ("<h1>Welcome Admin</h1>");
	}

	@GetMapping(value = "/username")
	@ResponseBody
	public String currentUserName(Principal principal) {
		return principal.getName();
	}
}
