package f54148.adminication.service;

import f54148.adminication.dto.*;
import f54148.adminication.entity.*;
import f54148.adminication.exceptions.EmailTakenException;
import f54148.adminication.exceptions.UsernameTakenException;
import f54148.adminication.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeacherService {

	private final TeacherRepository teacherRepository;

	private final TeachingService teachingService;	
	
	private final LessonService lessonService;
	
	private final ModelMapper modelMapper;
	
	private final RoleService roleService;
	
	private final PasswordEncoder encoder  = new BCryptPasswordEncoder();
	
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
		return opTeacher.orElse(null);
	}

	public boolean addTeacher(Teacher teacher) {

		teacherRepository.save(teacher);
		return true;
	}

	public boolean deleteTeacher(Long teacherId) {
		if (teacherRepository.findById(teacherId).isPresent()) {
			teacherRepository.deleteById(teacherId);
			return true;
		} else {
			return false;
		}
	}

	public boolean updateTeacher(Teacher t) {

		teacherRepository.save(t);
		return true;
	}

	public List<Course> getCoursesByTeacherId(Long teacherId) {
		Optional<Teacher> opTeacher = teacherRepository.findById(teacherId);
		if (opTeacher.isPresent()) {
			Teacher t = opTeacher.get();

			List<Course> courses = new ArrayList<>();

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

			List<Course> courses = new ArrayList<>();

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

		List<Teacher> substitutes = new ArrayList<>();

		for (Teaching t : teachings) {
			substitutes.add(t.getSubstitute());
		}

		return substitutes;
	}

	public Set<Lesson> getLessonsByTeacherId(Long teacherId) {
		Optional<Teacher> opTeacher = teacherRepository.findById(teacherId);
		return opTeacher.map(Teacher::getLessons).orElse(null);
	}

	public Set<File> getFilesyTeacherId(Long teacherId) {
		Optional<Teacher> opTeacher = teacherRepository.findById(teacherId);
		return opTeacher.map(Teacher::getFiles).orElse(null);
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
	
	public DisplayTeacherDTO convertToDisplayTeacherDTO(Teacher t) {
		
		DisplayTeacherDTO  dto = new DisplayTeacherDTO();
		dto.setId(t.getId());
		dto.setName(t.getName() + " " + t.getLastName());
		dto.setGender(t.getGender().name());
		dto.setTeaching(t.getTeaching().size());
		dto.setSubstituting(t.getSubstituting().size());
		
		return dto;
		
	}

	public List<DisplayTeacherDTO> getAllDisplayTeacherDTO() {
		List<Teacher> teachers = getTeachers();
		
		List<DisplayTeacherDTO> dtos = new ArrayList<>();
		for(Teacher t: teachers) {
			dtos.add(convertToDisplayTeacherDTO(t));
		}
		
		return dtos;
	}

	public String adminAddTeacher(AddTeacherDTO teacher) {
				
			Teacher t = convertAddTeacherDTOToTeacher(teacher);
			t = teacherRepository.save(t);
			for(AddTeacherTeachingDTO dto: teacher.getTeachings()) {
				teachingService.createTeaching(dto,t.getId());
				
			}
			
			return "Teacher has been added successfully!";

		
	}

	private Teacher convertAddTeacherDTOToTeacher(AddTeacherDTO teacher) {
		
		Teacher t = modelMapper.map(teacher, Teacher.class);
		t.setAccountNonExpired(true);
		t.setAccountNonLocked(true);
		t.setCredentialsNonExpired(true);
		t.setEnabled(true);
		t.setRole(roleService.getRoleByName("ROLE_TEACHER"));
		t.setPassword(encoder.encode(teacher.getPassword()));
		
		return t;
	}


    public CourseReportTeacherDTO convertToCourseReportTeacherDTO(Teacher teacher,Teacher sub, Double pricePerLesson) {
		CourseReportTeacherDTO dto = new CourseReportTeacherDTO();
		dto.setTeacherId(teacher.getId());
		dto.setName(teacher.getName() + " " + teacher.getLastName());
		dto.setPricePerLesson(pricePerLesson);
		if(sub!=null){
			dto.setSubId(sub.getId());
			dto.setSubName(sub.getName() + " " + sub.getLastName());
		}
		else{
			dto.setSubId(null);
			dto.setSubName(null);
		}
		return dto;
    }
}
