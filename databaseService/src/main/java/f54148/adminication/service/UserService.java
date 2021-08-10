package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.DisplayUserDTO;
import f54148.adminication.dto.EditUserDTO;
import f54148.adminication.dto.NotificationDTO;
import f54148.adminication.entity.Draft;
import f54148.adminication.entity.Gender;
import f54148.adminication.entity.MessageStatus;
import f54148.adminication.entity.Notification;
import f54148.adminication.entity.Role;
import f54148.adminication.entity.User;
import f54148.adminication.exceptions.UserNotFoundException;
import f54148.adminication.repository.UserRepository;

@Service

public class UserService {

	private final UserRepository userRepository;

	private final NotificationService notificationService;

	private final ModelMapper modelMapper;
	
	private final PasswordEncoder encoder  = new BCryptPasswordEncoder();

	public UserService(UserRepository userRepository, @Lazy NotificationService notificationService,  ModelMapper modelMapper) {
		super();
		this.userRepository = userRepository;
		this.notificationService = notificationService;
		this.modelMapper = modelMapper;

	}

	public List<User> getUsers() {
		return new ArrayList<>(userRepository.findAll());
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

		userRepository.save(user);
		return true;
	}
	
	public void updateUser(User user) {

		userRepository.save(user);
	}


	public boolean updateUser(EditUserDTO dto) {

		User u = getUserById(dto.getId());

		if (u != null) {

			u.setUsername(dto.getUsername());
			u.setName(dto.getName());
			u.setLastName(dto.getLastName());
			u.setEmail(dto.getEmail());
			if(dto.getChangedPassword()) {
				u.setPassword(encoder.encode(dto.getPassword()));
			}
			userRepository.save(u);
			return true;

		} else {
			return false;
		}
	}

	public boolean deleteUser(Long userId) {
		if (userRepository.findById(userId).isPresent()) {
			userRepository.deleteById(userId);
			return true;
		} else {
			return false;
		}
	}

	public List<Notification> getNotificationsReceivedByUser(Long userId) {
		Optional<User> opUser = userRepository.findById(userId);
		return opUser.map(User::getNotificationsReceived).orElse(null);
	}

	public List<Notification> getNotificationsSendByUser(Long id) {
		return notificationService.getNotificationsSendByUserID(id);
	}

	public List<Draft> getDraftsByUser(Long userId) {
		Optional<User> opUser = userRepository.findById(userId);
		return opUser.map(User::getDrafts).orElse(null);
	}

	public Role getUserRole(Long userId) {
		Optional<User> opUser = userRepository.findById(userId);
		return opUser.map(User::getRole).orElse(null);
	}

	public DisplayUserDTO convertToCreateUserDTO(User user) {
		
		DisplayUserDTO dto = modelMapper.map(user, DisplayUserDTO.class);
		
		List<Notification> nots = getNotificationsReceivedByUser(user.getId());
		
		nots.removeIf(not->not.getStatus()!=MessageStatus.SEND);
		
		List<NotificationDTO> dtoNots = new ArrayList<>();
		
		for(Notification n : nots) {
			dtoNots.add(notificationService.convertToNotificationDTO(n));
		}
		dto.setNotifications(dtoNots);
		
		return dto;

	}

	public DisplayUserDTO getCreateUserDTOByUsername(@Min(1) String username) {
		return convertToCreateUserDTO(getUserByUsername(username));
	}

	public boolean emailExists(String email) {
		Optional<User> opUser = userRepository.findByEmail(email);
		return opUser.isPresent();
	}

	public boolean usernameExists(String username) {
		Optional<User> opUser = userRepository.findByUsername(username);
		return opUser.isPresent();
	}

	public boolean encodeAllPassowords() {
		List<User> users = this.getUsers();
		
		try {
		for(User u: users) {
			u.setPassword(encoder.encode(u.getPassword()));
			userRepository.save(u);
		}
		
		return true;}
		catch (Exception e) {
			return false;
		}
		
	}

	public boolean setAllGender() {
		
		List<User> users = this.getUsers();
		
		try {
		for(User u: users) {
			u.setGender(Gender.FEMALE);
			userRepository.save(u);
		}
		
		return true;}
		catch (Exception e) {
			return false;
		}
	}

	public boolean encodeSinglePassword(Long id) {
		try {
		User u = this.getUserById(id);
		u.setPassword(encoder.encode(u.getPassword()));
		userRepository.save(u);
		return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public User getAdmin() {
		
		return getUserById((long) 1);
		
	}

}
