package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.DisplayUserDTO;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.File;
import f54148.adminication.entity.Lesson;
import f54148.adminication.entity.Teacher;
import f54148.adminication.entity.Teaching;
import f54148.adminication.repository.TeacherRepository;

@Service
public class TeacherService {

	private final TeacherRepository teacherRepository;

	private final TeachingService teachingService;	
	
	private final ModelMapper modelMapper;
	
	private final RoleService roleService;
	
	public TeacherService(TeacherRepository teacherRepository,@Lazy TeachingService teachingService, @Lazy RoleService roleService,ModelMapper modelMapper) {
		super();
		this.teacherRepository = teacherRepository;
		this.teachingService = teachingService;
		this.roleService = roleService;
		this.modelMapper = modelMapper;
	}

	public List<Teacher> getTeachers() {
		List<Teacher> teachersList = new ArrayList<>();
		teacherRepository.findAll().forEach(teachersList::add);
		return teachersList;
	}

	public Teacher getTeacherById(Long teacherId) {
		Optional<Teacher> opTeacher = teacherRepository.findById(teacherId);
		if (opTeacher.isPresent()) {
			return opTeacher.get();
		} else {
			return null;
		}
	}

	public boolean addTeacher(Teacher teacher) {
		
		if (teacherRepository.save(teacher) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteTeacher(Long teacherId) {
		if (teacherRepository.findById(teacherId) != null) {
			teacherRepository.deleteById(teacherId);
			return true;
		} else {
			return false;
		}
	}

	public boolean updateTeacher(Teacher t) {
		if (teacherRepository.save(t) != null) {

			return true;
		} else {
			return false;
		}
	}

	public List<Course> getCoursesByTeacherId(Long teacherId) {
		Optional<Teacher> opTeacher = teacherRepository.findById(teacherId);
		if (opTeacher.isPresent()) {
			Teacher t = opTeacher.get();

			List<Course> courses = new ArrayList<Course>();

			for (Teaching teach : t.getTeaching()) {

				courses.add(teach.getCourse());
			}
			return courses;

		} else {
			return null;
		}
	}

	public List<Course> getsubstituteCoursesByTeacherId(Long teacherId) {

		Optional<Teacher> opTeacher = teacherRepository.findById(teacherId);
		if (opTeacher.isPresent()) {
			Teacher t = opTeacher.get();

			List<Course> courses = new ArrayList<Course>();

			for (Teaching teach : t.getSubstituting()) {

				courses.add(teach.getCourse());
			}
			return courses;

		} else {
			return null;
		}
	}

	public List<Teacher> getSubstitutesByCourseId(Long courseId) {

		List<Teaching> teachings = teachingService.getTeachingsByCourseId(courseId);

		List<Teacher> substitutes = new ArrayList<Teacher>();

		for (Teaching t : teachings) {
			substitutes.add(t.getSubstitute());
		}

		return substitutes;
	}

	public Set<Lesson> getLessonsByTeacherId(Long teacherId) {
		Optional<Teacher> opTeacher = teacherRepository.findById(teacherId);
		if (opTeacher.isPresent()) {
			return opTeacher.get().getLessons();
		} else {
			return null;
		}
	}

	public Set<File> getFilesyTeacherId(Long teacherId) {
		Optional<Teacher> opTeacher = teacherRepository.findById(teacherId);
		if (opTeacher.isPresent()) {
			return opTeacher.get().getFiles();
		} else {
			return null;
		}
	}

	public Teacher convertToTeacher(DisplayUserDTO userDTO) {
		Teacher t = modelMapper.map(userDTO, Teacher.class);		
		t.setAccountNonExpired(true);
		t.setAccountNonLocked(true);
		t.setCredentialsNonExpired(true);
		t.setEnabled(true);
		t.setRole(roleService.getRoleByName("ROLE_TEACHER"));

		return t;
	}

}
