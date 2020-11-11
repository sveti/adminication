package f54148.adminication.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseDetail;
import f54148.adminication.entity.CourseWaitingList;
import f54148.adminication.entity.Enrollment;
import f54148.adminication.entity.File;
import f54148.adminication.entity.Lesson;
import f54148.adminication.entity.Schedule;
import f54148.adminication.entity.Student;
import f54148.adminication.entity.Teacher;
import f54148.adminication.entity.Teaching;
import f54148.adminication.repository.CourseRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService {


	private final CourseRepository courseRepository;

	public List<Course> getCourses() {
		List<Course> courseList = new ArrayList<>();
		courseRepository.findAll().forEach(courseList::add);
		return courseList;
	}

	public Course getCourseById(Long courseId) {
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if (opCourse.isPresent()) {
			return opCourse.get();
		} else {
			return null;
		}
	}

	public boolean addCourse(Course course) {
		if (courseRepository.save(course) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateCourse(Course course) {
		if (courseRepository.save(course) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteCourse(Long courseId) {
		if (courseRepository.findById(courseId) != null) {
			courseRepository.deleteById(courseId);
			return true;
		} else {
			return false;
		}
	}

	public List<Student> getStudentsByCourseId(Long id) {
		Optional<Course> opCourse = courseRepository.findById(id);
		if (opCourse.isPresent()) {
			Course c = opCourse.get();

			List<Student> students = new ArrayList<Student>();

			for (Enrollment e : c.getEnrollments()) {
				students.add(e.getStudent());
			}
			return students;

		} else {
			return null;
		}
	}

	public List<Teacher> getTeachersByCourseId(Long id) {
		Optional<Course> opCourse = courseRepository.findById(id);
		if (opCourse.isPresent()) {
			Course c = opCourse.get();

			List<Teacher> teachers = new ArrayList<Teacher>();

			for (Teaching e : c.getTeaching()) {
				teachers.add(e.getTeacher());
			}
			return teachers;

		} else {
			return null;
		}
	}

	public Set<Schedule> getScheduleByCourseId(Long courseId) {
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if (opCourse.isPresent()) {
			return opCourse.get().getCourseSchedule();
		} else {
			return null;
		}
	}

	public Collection<CourseWaitingList> getCourseWaitingList(Long courseId) {
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if (opCourse.isPresent()) {
			return opCourse.get().getCourseWaitingList();
		} else {
			return null;
		}
	}

	public List<Student> getStudentsWaitingByCourseId(Long courseId) {
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if (opCourse.isPresent()) {
			Course c = opCourse.get();

			List<Student> students = new ArrayList<Student>();

			for (CourseWaitingList cw : c.getCourseWaitingList()) {
				students.add(cw.getStudent());
			}

			return students;
		} else {
			return null;
		}
	}

	public Set<Lesson> getLessonsByCourseId(Long courseId) {
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if (opCourse.isPresent()) {
			return opCourse.get().getLessons();
		} else {
			return null;
		}
	}

	public Set<File> getFilesByCourseId(Long courseId) {
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if (opCourse.isPresent()) {
			return opCourse.get().getFiles();
		} else {
			return null;
		}
	}

	public Set<CourseDetail> getCourseDetailsByCourseId(Long courseId) {
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if (opCourse.isPresent()) {
			return opCourse.get().getDetails();
		} else {
			return null;
		}
	}

	public List<Teacher> getSubstitutesByCourseId(Long id) {
		Optional<Course> opCourse = courseRepository.findById(id);
		if (opCourse.isPresent()) {
			Course c = opCourse.get();

			List<Teacher> teachers = new ArrayList<Teacher>();

			for (Teaching e : c.getTeaching()) {
				//if the teaching has a substitute add the sub teacher to the list
				if(e.getSubstitute()!= null) {
					teachers.add(e.getSubstitute());
				}
			}
			return teachers;

		} else {
			return null;
		}
	}

}
