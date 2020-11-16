package f54148.adminication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import f54148.adminication.entity.User;

public interface UserRespository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
} 
