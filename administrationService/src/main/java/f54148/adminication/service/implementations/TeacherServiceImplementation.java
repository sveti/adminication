package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.DisplayUserDTO;
import f54148.adminication.service.TeacherService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class TeacherServiceImplementation implements TeacherService {
	
	 private final RestTemplate restTemplate;
		
	 private final PasswordEncoder encoder  = new BCryptPasswordEncoder();


}
