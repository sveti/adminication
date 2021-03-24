package f54148.adminication.service;

import java.util.List;

import javax.validation.constraints.Min;

import f54148.adminication.dto.DisplayUserDTO;
import f54148.adminication.dto.EditUserDTO;

public interface UserService {
	
	List<DisplayUserDTO> getUsers();
	
	DisplayUserDTO getUser(String username);

	String validateEmail(String email);

	String validateUsername(String username);

	String editUser(EditUserDTO editUserDTO);
	

}
