package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Attendance;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.Lesson;
import f54148.adminication.entity.Student;
import f54148.adminication.entity.Teacher;
import f54148.adminication.repository.LessonRepository;

@Service
public class LessonService {

	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private CourseService courseService;

	public List<Lesson> getLessons() {
		List<Lesson> lessonList = new ArrayList<>();
		lessonRepository.findAll().forEach(lessonList::add);
		return lessonList;
	}

	public Lesson getLessonById(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		if (opLesson.isPresent()) {
			return opLesson.get();
		} else {
			return null;
		}
	}

	public boolean addLesson(Lesson lesson) {
		if (lessonRepository.save(lesson) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateLesson(Lesson lesson) {
		if (lessonRepository.save(lesson) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteLesson(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		if (opLesson.isPresent()) {
			Lesson l = opLesson.get();

			Teacher t = l.getTeacher();
			t.getLessons().remove(l);
			teacherService.updateTeacher(t);

			Course c = l.getCourse();
			c.getLessons().remove(l);
			courseService.updateCourse(c);

			lessonRepository.deleteById(lessonId);
			return true;
		} else {
			return false;
		}
	}

	public List<Attendance> getAttendancesByLessonId(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		if (opLesson.isPresent()) {
			return opLesson.get().getAttendances();
		} else {
			return null;
		}
	}

	public List<Student> getPresentAttendancesByLessonId(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		if (opLesson.isPresent()) {
			List<Attendance> allAttendances = opLesson.get().getAttendances();
			List<Student> students = new ArrayList<Student>();
			
			for(Attendance a: allAttendances) {
				if(a.isAttended()) {
					students.add(a.getStudent());
				}
			}
			return students;
		} else {
			return null;
		}	
		
	}

	public List<Student> getMissingAttendancesByLessonId(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		if (opLesson.isPresent()) {
			List<Attendance> allAttendances = opLesson.get().getAttendances();
			List<Student> students = new ArrayList<Student>();
			
			for(Attendance a: allAttendances) {
				if(!a.isAttended()) {
					students.add(a.getStudent());
				}
			}
			return students;
		} else {
			return null;
		}
	}

}
