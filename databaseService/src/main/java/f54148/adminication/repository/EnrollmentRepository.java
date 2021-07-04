package f54148.adminication.repository;

import org.springframework.data.repository.CrudRepository;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.Enrollment;
import f54148.adminication.entity.Student;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {

	Enrollment findByStudentAndCourse(Student s, Course c);

}
