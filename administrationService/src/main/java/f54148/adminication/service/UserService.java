package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.UserDTO;

public interface UserService {
	
	List<UserDTO> getUsers();
	
	UserDTO getUser(@Min(1) long id);

}
