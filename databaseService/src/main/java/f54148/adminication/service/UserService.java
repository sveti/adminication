package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.User;
import f54148.adminication.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
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
		   return null;
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
	

}
