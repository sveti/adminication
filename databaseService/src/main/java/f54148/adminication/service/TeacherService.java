package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.DisplayUserDTO;
import f54148.adminication.dto.LessonSalaryDTO;
import f54148.adminication.dto.MonthlyTeacherSalaryDTO;
import f54148.adminication.dto.TeacherForCourseDTO;
import f54148.adminication.dto.TeachingSalaryDTO;
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
	
	private final LessonService lessonService;
	
	private final ModelMapper modelMapper;
	
	private final RoleService roleService;
	
	public TeacherService(TeacherRepository teacherRepository,@Lazy LessonService lessonService,@Lazy TeachingService teachingService, @Lazy RoleService roleService,ModelMapper modelMapper) {
		super();
		this.teacherRepository = teacherRepository;
		this.teachingService = teachingService;
		this.roleService = roleService;
		this.modelMapper = modelMapper;
		this.lessonService = lessonService;
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

	public MonthlyTeacherSalaryDTO getTeacherStatistics(Long teacherId, Integer month, Integer year) {
		
		MonthlyTeacherSalaryDTO dto = new MonthlyTeacherSalaryDTO();
		
		List<Lesson> lessons = lessonService.getLessonsOfTeacherByMonthAndYear(teacherId, month, year);
		List<LessonSalaryDTO> dtoLessons = new ArrayList<>();
		for(Lesson l: lessons) {
			dtoLessons.add(lessonService.convertToLessonSalaryDTO(l));
		}
		
		dto.setLessons(dtoLessons);
				
		List<Teaching> teachings = teachingService.getTeachingsByTeacherId(teacherId);
		List<TeachingSalaryDTO> dtoTeachings = new ArrayList<>();
		
		for(Teaching t: teachings) {
			dtoTeachings.add(teachingService.convertToTeachingSalaryDTO(t));
		}
		
		dto.setTeachings(dtoTeachings);
		
		Set <Teaching> subs  = getTeacherById(teacherId).getSubstituting();
		List<TeachingSalaryDTO> dtoSubs = new ArrayList<>();
		
		for(Teaching t: subs) {
			dtoSubs.add(teachingService.convertToTeachingSalaryDTO(t));
		}
		
		dto.setSubstitutings(dtoSubs);
		
		return dto;
	}
	
	public TeacherForCourseDTO convertToTeacherForCourseDTO(Teacher t) {
		
		TeacherForCourseDTO dto = modelMapper.map(t, TeacherForCourseDTO.class);
		dto.setLabel(t.getName() +" "+t.getLastName());
		
		return dto;
	}

	public List<TeacherForCourseDTO> getAllTeacherForCourseDTO() {
		
		List<Teacher> allTeachers = getTeachers();
		List<TeacherForCourseDTO> dtos = new ArrayList<>();
		
		for(Teacher t: allTeachers) {
			dtos.add(convertToTeacherForCourseDTO(t));
		}
		
		return dtos;
	}


}
