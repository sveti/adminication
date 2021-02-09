package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.DisplayUserDTO;
import f54148.adminication.entity.Draft;
import f54148.adminication.entity.Gender;
import f54148.adminication.entity.Notification;
import f54148.adminication.entity.Role;
import f54148.adminication.entity.Teacher;
import f54148.adminication.entity.User;
import f54148.adminication.exceptions.UserNotFoundException;
import f54148.adminication.repository.UserRepository;

@Service

public class UserService {

	private final UserRepository userRepository;
	
	private final NotificationService notificationService;
	
	private final RoleService roleService;
	
	 private final ModelMapper modelMapper;

	public UserService(UserRepository userRepository, @Lazy NotificationService notificationService, RoleService roleService, ModelMapper modelMapper) {
		super();
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.notificationService = notificationService;
		this.modelMapper = modelMapper;
	
	}

	public List<User> getUsers() {
		List<User> userList = new ArrayList<>();
		userRepository.findAll().forEach(userList::add);
		return userList;
	}

	public User getUserById(Long userId) {
		Optional<User> opUser = userRepository.findById(userId);
		if (opUser.isPresent()) {
			return opUser.get();
		} else {
			throw new UserNotFoundException("Invalid user id " + userId);
		}
	}
	
	public User getUserByUsername(String username) {
		Optional<User> opUser = userRepository.findByUsername(username);
		if (opUser.isPresent()) {
			return opUser.get();
		} else {
			throw new UserNotFoundException("Invalid username " + username);
		}
	}
	
	public User getUserByEmail(String email) {
		Optional<User> opUser = userRepository.findByEmail(email);
		if (opUser.isPresent()) {
			return opUser.get();
		} else {
			throw new UserNotFoundException("Invalid email " + email);
		}
	}

	public boolean addUser(User user) {

		if (userRepository.save(user) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateUser(User user) {
		if (userRepository.save(user) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteUser(Long userId) {
		if (userRepository.findById(userId) != null) {
			userRepository.deleteById(userId);
			return true;
		} else {
			return false;
		}
	}

	public List<Notification> getNotificationsReceivedByUser(Long userId) {
		Optional<User> opUser = userRepository.findById(userId);
		if (opUser.isPresent()) {
			return opUser.get().getNotificationsReceived();
		} else {
			return null;
		}
	}

	public List<Notification> getNotificationsSendByUser(Long id) {
		return notificationService.getNotificationsSendByUserID(id);
	}

	public List<Draft> getDraftsByUser(Long userId) {
		Optional<User> opUser = userRepository.findById(userId);
		if (opUser.isPresent()) {
			return opUser.get().getDrafts();
		} else {
			return null;
		}
	}

	public Role getUserRole(Long userId) {
		Optional<User> opUser = userRepository.findById(userId);
		if (opUser.isPresent()) {
			return opUser.get().getRole();
		} else {
			return null;
		}
	}
	public DisplayUserDTO convertToCreateUserDTO(User user) {
		
		return modelMapper.map(user, DisplayUserDTO.class);
        
    }
	
	public DisplayUserDTO getCreateUserDTOById(@Min(1) Long userId) {
		return convertToCreateUserDTO(getUserById(userId));
	}
	

	

}
