package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.TeachingSalaryDTO;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.Teacher;
import f54148.adminication.entity.Teaching;
import f54148.adminication.repository.TeachingRepository;

@Service
public class TeachingService {

	private final TeachingRepository teachingRepository;

	private final CourseService courseService;

	private final TeacherService teacherService;
	
	private final ModelMapper modelMapper;
	

	public TeachingService(TeachingRepository teachingRepository,@Lazy CourseService courseService,
			@Lazy TeacherService teacherService, @Lazy ModelMapper modelMapper) {
		super();
		this.teachingRepository = teachingRepository;
		this.courseService = courseService;
		this.teacherService = teacherService;
		this.modelMapper = modelMapper;
	}

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
		teachings.removeIf(teaching->teaching.getCourse().getId()!=courseId);
		return teachings;

	}
	

	public List<Teaching> getTeachingsByTeacherId(Long teacherId) {
		List<Teaching> teachings = getTeachings();
		teachings.removeIf(teaching->teaching.getTeacher().getId()!=teacherId);
		return teachings;

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
	
	TeachingSalaryDTO convertToTeachingSalaryDTO(Teaching t) {
		TeachingSalaryDTO dto =  modelMapper.map(t,TeachingSalaryDTO.class);
		dto.setCourseSignedUp(t.getCourse().getEnrollments().size());
		dto.setCourseTitle(t.getCourse().getTitle());
		return dto;
		
	}

	public Teaching addTeaching(Long teacherId, Long courseId, Double salary) {
		
		Teaching t = new Teaching();
		t.setTeacher(teacherService.getTeacherById(teacherId));
		t.setCourse(courseService.getCourseById(courseId));
		t.setSalaryPerStudent(salary);
		
		return teachingRepository.save(t);
	
	}

}
