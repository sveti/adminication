package f54148.adminication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.MyUserDetails;
import f54148.adminication.entity.User;
import f54148.adminication.repository.UserRespository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRespository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user =  userRepository.findByUsername(username);
		
		user.orElseThrow( () -> new UsernameNotFoundException("Not found " + username));
		
		
		return user.map(MyUserDetails::new).get();
		
		
	}

}
