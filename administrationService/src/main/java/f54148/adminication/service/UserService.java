package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.CreateUserDTO;

public interface UserService {
	
	List<CreateUserDTO> getUsers();
	
	CreateUserDTO getUser(@Min(1) long id);
	
	String createUser(CreateUserDTO userDTO);

}
