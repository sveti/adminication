package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.DisplayUserDTO;
import f54148.adminication.dto.EditUserDTO;
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

	@Override
	public String validateEmail(String email) {
		String exists = restTemplate.getForObject("http://databaseService/users/validateEmail/{email}",String.class, email);
		return exists;
	}

	@Override
	public String validateUsername(String username) {
		String exists = restTemplate.getForObject("http://databaseService/users/validateUsername/{username}",String.class, username);
		return exists;
	}

	@Override
	public String editUser(EditUserDTO editUserDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EditUserDTO> requestEntity = new HttpEntity<>(editUserDTO, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://databaseService/users/updateUser", HttpMethod.PUT,requestEntity,String.class);
		return response.getBody();
	}
	
}
