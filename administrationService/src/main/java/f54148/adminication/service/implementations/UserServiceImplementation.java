package f54148.adminication.service.implementations;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.UserDTO;
import f54148.adminication.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class UserServiceImplementation implements UserService{
	
	@Override
	public List<UserDTO> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO getUser(@Min(1) long id) {
		
		String serviceUrl = "http://localhost:8080/users/user/{id}";
		
		ResponseEntity<UserDTO> responseEntity = 
				new RestTemplate().getForEntity(
						   serviceUrl, UserDTO.class,  id);
		
		
		return responseEntity.getBody();
	}

}
