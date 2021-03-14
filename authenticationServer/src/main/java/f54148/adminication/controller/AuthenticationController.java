package f54148.adminication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.models.AuthenticationRequest;
import f54148.adminication.models.AuthenticationResponse;
import f54148.adminication.service.UserService;
import f54148.adminication.utils.JWTUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserService userService;
	
	private final JWTUtil jwtUtil;
	
	@PostMapping(path = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}

	
	@GetMapping(path = "/hi")
	public @ResponseBody String greeting() {
		return "hi";
	}

}
