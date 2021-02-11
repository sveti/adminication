package f54148.adminication.service;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.AttendanceDTO;
import f54148.adminication.dto.LessonDTO;
import f54148.adminication.dto.UpdateLessonDescriptionDTO;
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
	
	private final ModelMapper modelMapper;

	public LessonService(LessonRepository lessonRepository, @Lazy TeacherService teacherService,
			 @Lazy CourseService courseService,ModelMapper modelMapper) {
		super();
		this.lessonRepository = lessonRepository;
		this.teacherService = teacherService;
		this.courseService = courseService;
		this.modelMapper = modelMapper;
	}

	public List<Lesson> getLessons() {
		List<Lesson> lessonList = new ArrayList<>();
		lessonRepository.findAll().forEach(lessonList::add);
		return lessonList;
	}
	
	public List<Lesson> getLessonsByCourseId(Long courseID) {
		List<Lesson> lessonList = new ArrayList<>();
		lessonRepository.findAll().forEach(lessonList::add);
		lessonList.removeIf(lesson->lesson.getCourse().getId()!=courseID);
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
	
	public LessonDTO convertToLessonDTO(Lesson lesson) {
		LessonDTO lessonDTO =  modelMapper.map(lesson, LessonDTO.class);
		return lessonDTO;
	}
	
	public Lesson convertToLesson(LessonDTO lessonDTO) {
		Lesson lesson =  modelMapper.map(lessonDTO, Lesson.class);
		return lesson;
	}
	
	
	public LessonDTO getLessonDTOById(Long lessonId) {
		return convertToLessonDTO(getLessonById(lessonId));
	}

	public boolean addLessonDTO(LessonDTO lessonDTO) {
		return addLesson(convertToLesson(lessonDTO));
	}
	
	public List<LessonDTO> getLessonsByTeacherIdAndCourseId(Long teacherId, Long courseId){
		
		List<Lesson> allCourseLessons = getLessonsByCourseId(courseId);
		List<LessonDTO> filtered = new ArrayList<>();
		for(Lesson l : allCourseLessons) {
			if(l.getTeacher().getId()==teacherId) {
				filtered.add(convertToLessonDTO(l));
			}
		}
		return filtered;
	}
	
	public AttendanceDTO convertToAttendanceDTO(Attendance a) {
		AttendanceDTO attendanceDTO =  modelMapper.map(a, AttendanceDTO.class);
		return attendanceDTO;
	}
	

	
	public List<AttendanceDTO> getAttandancesOfLesson(Long lessonId){
		
		Set<Attendance> at = getAttendancesByLessonId(lessonId);
		
		List<AttendanceDTO> attDTO = new ArrayList<>();
		
		for(Attendance a: at) {
			attDTO.add(convertToAttendanceDTO(a));
		}
		
		return attDTO;
		
	}
	
	public List<AttendanceDTO> getAttendanceDTOOfCourse(Long courseId){
		
		List<Lesson> lessons = getLessonsByCourseId(courseId);
		
		List<AttendanceDTO> attDTO = new ArrayList<>();
		
		for(Lesson l: lessons) {
			attDTO.addAll(getAttandancesOfLesson(l.getId()));
		}
		
		return attDTO;
	}
	
	public boolean updateLessonDescription(UpdateLessonDescriptionDTO dto) {
		
		Lesson l = getLessonById(dto.getId());
		l.setDescription(dto.getDescription());
		
		return updateLesson(l);
		
	}
	
}
