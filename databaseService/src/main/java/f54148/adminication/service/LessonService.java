package f54148.adminication.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Attendance;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.Lesson;
import f54148.adminication.entity.Student;
import f54148.adminication.entity.Teacher;
import f54148.adminication.repository.LessonRepository;

@Service
public class LessonService {

	private final LessonRepository lessonRepository;

	private final TeacherService teacherService;

	private final CourseService courseService;
	
	

	public LessonService(LessonRepository lessonRepository, @Lazy TeacherService teacherService,
			 @Lazy CourseService courseService) {
		super();
		this.lessonRepository = lessonRepository;
		this.teacherService = teacherService;
		this.courseService = courseService;
	}

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

	public Set<Attendance> getAttendancesByLessonId(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		if (opLesson.isPresent()) {
			return opLesson.get().getAttendances();
		} else {
			return null;
		}
	}

	public Set<Student> getPresentAttendancesByLessonId(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		if (opLesson.isPresent()) {
			Set<Attendance> allAttendances = opLesson.get().getAttendances();
			Set<Student> students = new HashSet<Student>();
			
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

	public Set<Student> getMissingAttendancesByLessonId(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		if (opLesson.isPresent()) {
			Set<Attendance> allAttendances = opLesson.get().getAttendances();
			Set<Student> students = new HashSet<Student>();
			
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
