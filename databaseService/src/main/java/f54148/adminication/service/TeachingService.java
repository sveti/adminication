package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.Teacher;
import f54148.adminication.entity.Teaching;
import f54148.adminication.repository.TeachingRepository;

@Service
public class TeachingService {

	@Autowired
	private TeachingRepository teachingRepository;

	@Autowired
	private CourseService courseService;

	@Autowired
	private TeacherService teacherService;

	public List<Teaching> getTeachings() {
		List<Teaching> teachingList = new ArrayList<>();
		teachingRepository.findAll().forEach(teachingList::add);
		return teachingList;
	}

	public Teaching getTeachingById(Long teachingId) {
		Optional<Teaching> opTeaching = teachingRepository.findById(teachingId);
		if (opTeaching.isPresent()) {
			return opTeaching.get();
		} else {
			return null;
		}
	}

	public List<Teaching> getTeachingsByCourseId(Long courseId) {
		List<Teaching> teachings = getTeachings();

		List<Teaching> current = new ArrayList<Teaching>();

		for (Teaching t : teachings) {
			if (t.getCourse().getId() == courseId) {
				current.add(t);
			}
		}

		return current;

	}

	public boolean addTeaching(Teaching teaching) {
		if (teachingRepository.save(teaching) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateTeaching(Teaching teaching) {
		if (teachingRepository.save(teaching) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteTeaching(Long teachingId) {
		Optional<Teaching> opTeaching = teachingRepository.findById(teachingId);
		if (opTeaching.isPresent()) {
			Teaching t = opTeaching.get();

			Course c = t.getCourse();
			c.getTeaching().remove(t);

			courseService.updateCourse(c);

			Teacher teacher = t.getTeacher();
			teacher.getTeaching().remove(t);

			teacherService.updateTeacher(teacher);

			Teacher substitute = t.getSubstitute();
			substitute.getSubstituting().remove(t);

			teacherService.updateTeacher(substitute);

			teachingRepository.deleteById(teachingId);
			return true;
		} else {
			return false;
		}
	}

}
