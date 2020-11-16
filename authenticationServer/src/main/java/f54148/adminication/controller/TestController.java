package f54148.adminication.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@GetMapping(path = "/hi")
	public @ResponseBody String greeting() {
		return "hi";
	}

	@GetMapping(path = "/")
	public @ResponseBody String home() {
		return ("<h1>Welcome</h1>");
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
