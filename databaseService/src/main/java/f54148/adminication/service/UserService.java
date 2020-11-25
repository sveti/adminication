package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.TestUserDTO;
import f54148.adminication.entity.Draft;
import f54148.adminication.entity.Notification;
import f54148.adminication.entity.Role;
import f54148.adminication.entity.User;
import f54148.adminication.exceptions.UserNotFoundException;
import f54148.adminication.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service

public class UserService {

	private final UserRepository userRepository;
	
	private final NotificationService notificationService;
	
	private final PasswordEncoder encoder;
	
	 private final ModelMapper modelMapper = new ModelMapper();

	public UserService(UserRepository userRepository, @Lazy NotificationService notificationService) {
		super();
		this.userRepository = userRepository;
		this.notificationService = notificationService;
		this.encoder = new BCryptPasswordEncoder();
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

	public boolean addUser(User user) {
		
		user.setPassword(encoder.encode(user.getPassword()));
		
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
	private TestUserDTO convertToTestUserDTO(TestUserDTO user) {
        return modelMapper.map(user, TestUserDTO.class);
    }
	
	public TestUserDTO getTestDTOUser(long id){
		return modelMapper.map(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Invalid user Id: " + id)), TestUserDTO.class);	
	}

}
