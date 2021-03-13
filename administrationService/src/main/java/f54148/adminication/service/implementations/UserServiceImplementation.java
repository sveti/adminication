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
import f54148.adminication.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class UserServiceImplementation implements UserService{
	
    private final RestTemplate restTemplate;
	
    
	@Override
	public List<DisplayUserDTO> getUsers() {
		
		DisplayUserDTO users[] = restTemplate.getForObject("http://databaseService/users/users",DisplayUserDTO[].class);
		return Arrays.asList(users);
	}

	@Override
	public DisplayUserDTO getUser(String username) {
		
		DisplayUserDTO user = restTemplate.getForObject("http://databaseService/users/displayUserDTO/{username}",DisplayUserDTO.class, username);
		return user;
	}
}
