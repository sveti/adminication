package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.DisplayUserDTO;

public interface UserService {
	
	List<DisplayUserDTO> getUsers();
	
	DisplayUserDTO getUser(@Min(1) long id);
	

}
