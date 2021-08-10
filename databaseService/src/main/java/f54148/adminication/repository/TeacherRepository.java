package f54148.adminication.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import f54148.adminication.entity.Teacher;
import f54148.adminication.entity.User;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {

	Optional<Teacher> findByUsername(String username);

	Optional<Teacher> findByEmail(String email);

}