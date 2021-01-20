package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.CreateUserDTO;
import f54148.adminication.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class UserServiceImplementation implements UserService{
	
    private final RestTemplate restTemplate;
	
	private final PasswordEncoder encoder  = new BCryptPasswordEncoder();;
    
	@Override
	public List<CreateUserDTO> getUsers() {
		
		CreateUserDTO users[] = restTemplate.getForObject("http://databaseService/users/users",CreateUserDTO[].class);
		return Arrays.asList(users);
	}

	@Override
	public CreateUserDTO getUser(@Min(1) long id) {
		
		CreateUserDTO user = restTemplate.getForObject("http://databaseService/users/createUserDTO/{id}",CreateUserDTO.class, id);
		return user;
	}

	@Override
	public String createUser(CreateUserDTO userDTO) {
		
		userDTO.setPassword(encoder.encode(userDTO.getPassword()));
		
		String result = restTemplate.postForObject("http://databaseService/users/add", userDTO, String.class);
		return result;
	}

	@Override
	public String getUserRole(@Min(1) Long id) {
		String role = restTemplate.getForObject("http://databaseService/users/user/{id}/roleName",String.class, id);
		return role;
	}

}
