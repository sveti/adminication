package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import f54148.adminication.dto.AddParentDTO;
import f54148.adminication.dto.AddStudentDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
		
		DisplayUserDTO[] users = restTemplate.getForObject("http://databaseService/users/users",DisplayUserDTO[].class);
		assert users != null;
		return Arrays.asList(users);
	}

	@Override
	public DisplayUserDTO getUser(String username) {

		return restTemplate.getForObject("http://databaseService/users/displayUserDTO/{username}",DisplayUserDTO.class, username);
	}

	@Override
	public String validateEmail(String email) {
		return restTemplate.getForObject("http://databaseService/users/validateEmail/{email}",String.class, email);
	}

	@Override
	public String validateUsername(String username) {
		return restTemplate.getForObject("http://databaseService/users/validateUsername/{username}",String.class, username);
	}

	@Override
	public String editUser(EditUserDTO editUserDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EditUserDTO> requestEntity = new HttpEntity<>(editUserDTO, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://databaseService/users/updateUser", HttpMethod.PUT,requestEntity,String.class);

		restTemplate.exchange("http://keycloakadminserver/update", HttpMethod.PUT,requestEntity,String.class);

		return response.getBody();
	}

	@Override
	public String addParent(AddParentDTO addParentDTO) {

		ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/parents/add", addParentDTO, String.class);

		for(AddStudentDTO studentOfParentDTO : addParentDTO.getStudents()){
			restTemplate.postForEntity("http://keycloakadminserver/add", studentOfParentDTO, String.class);
		}
		restTemplate.postForEntity("http://keycloakadminserver/add", addParentDTO, String.class);

		return response.getBody();
	}
	
}
