package f54148.adminication.repository;

import org.springframework.data.repository.CrudRepository;

import f54148.adminication.entity.Event;
import f54148.adminication.entity.EventSignUp;
import f54148.adminication.entity.Student;

public interface EventSignUpRepository extends CrudRepository<EventSignUp, Long> {

	EventSignUp findByStudentAndEvent(Student s, Event e);

}
