package f54148.adminication.service;

import java.util.*;

import f54148.adminication.dto.*;
import org.modelmapper.ModelMapper;
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
	
	private final AttendanceService attendanceService;
	
	private final ModelMapper modelMapper;

	public LessonService(LessonRepository lessonRepository, @Lazy TeacherService teacherService,
			 @Lazy CourseService courseService,@Lazy AttendanceService attendanceService,ModelMapper modelMapper) {
		super();
		this.lessonRepository = lessonRepository;
		this.teacherService = teacherService;
		this.courseService = courseService;
		this.attendanceService = attendanceService;
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
		lessonList.removeIf(lesson-> !Objects.equals(lesson.getCourse().getId(), courseID));
		return lessonList;
	}
	
	public List<Lesson> getLessonsByTeacherId(Long teacherId) {
		List<Lesson> lessonList = new ArrayList<>();
		lessonRepository.findAll().forEach(lessonList::add);
		lessonList.removeIf(lesson->lesson.getTeacher().getId()!=teacherId);
		return lessonList;
	}


	public Lesson getLessonById(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		return opLesson.orElse(null);
	}

	public boolean addLesson(Lesson lesson) {
		lessonRepository.save(lesson);
		return true;
	}

	public boolean updateLesson(Lesson lesson) {
		lessonRepository.save(lesson);
		return true;
	}

	public boolean deleteLesson(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		if (opLesson.isPresent()) {
			Lesson l = opLesson.get();
			
			for(Attendance a: l.getAttendances()) {
				attendanceService.deleteAttendance(a.getId());
			}
			
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
		return opLesson.map(Lesson::getAttendances).orElse(null);
	}

	public Set<Student> getPresentAttendancesByLessonId(Long lessonId) {
		Optional<Lesson> opLesson = lessonRepository.findById(lessonId);
		if (opLesson.isPresent()) {
			Set<Attendance> allAttendances = opLesson.get().getAttendances();
			Set<Student> students = new HashSet<>();
			
			for(Attendance a: allAttendances) {
				if(a.getAttended()) {
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
			Set<Student> students = new HashSet<>();
			
			for(Attendance a: allAttendances) {
				if(!a.getAttended()) {
					students.add(a.getStudent());
				}
			}
			return students;
		} else {
			return null;
		}
	}
	
	public LessonDTO convertToLessonDTO(Lesson lesson) {
		return modelMapper.map(lesson, LessonDTO.class);
	}
	
	public StudentLessonDTO convertToStudentLessonDTO(Lesson lesson, Long studentId) {
		
		LessonDTO lessonDTO =  modelMapper.map(lesson, LessonDTO.class);
		StudentLessonDTO stDTO = new StudentLessonDTO(lessonDTO);
		
		for(Attendance a: lesson.getAttendances()) {
			if(a.getStudent().getId()==studentId) {
				stDTO.setAttended(a.getAttended());
			}
		}
		
		return stDTO;
	}
	
	
	public Lesson convertToLesson(LessonDTO lessonDTO) {
		return modelMapper.map(lessonDTO, Lesson.class);
	}
	
	
	public LessonDTO getLessonDTOById(Long lessonId) {
		return convertToLessonDTO(getLessonById(lessonId));
	}

	public boolean addLessonDTO(LessonDTO lessonDTO) {
		return addLesson(convertToLesson(lessonDTO));
	}
	
	public List<LessonDTO> getLessonsDTOByCourseId(Long courseId){
		
		List<Lesson> allCourseLessons = getLessonsByCourseId(courseId);
		List<LessonDTO> filtered = new ArrayList<>();
		for(Lesson l : allCourseLessons) {
	
				filtered.add(convertToLessonDTO(l));
			
		}
		return filtered;
	}
	
	public AttendanceDTO convertToAttendanceDTO(Attendance a) {
		return modelMapper.map(a, AttendanceDTO.class);
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

	public List<Lesson> getLessonsOfTeacherByMonthAndYear(Long teacherId, Integer month, Integer year) {
		
		List<Lesson> allLessons = getLessonsByTeacherId(teacherId);
		allLessons.removeIf(lesson->lesson.getDate().getMonthValue()!=month || lesson.getDate().getYear()!=year );
		
		return allLessons;
	}

	public LessonSalaryDTO convertToLessonSalaryDTO(Lesson l) {
		
		LessonSalaryDTO dto = modelMapper.map(l,LessonSalaryDTO.class);
		Set<Attendance> att= getAttendancesByLessonId(l.getId());
		att.removeIf(a-> !a.getAttended());
		dto.setAttended(att.size());
		return dto;
	}

    public CourseReportLesson convertToCourseReportLesson(Lesson lesson) {

		CourseReportLesson dto = new CourseReportLesson();
		dto.setLessonId(lesson.getId());
		dto.setDate(lesson.getDate());
		dto.setDescription(lesson.getDescription());
		dto.setTeacherId(lesson.getTeacher().getId());
		dto.setTeacherName(lesson.getTeacher().getName() + " " + lesson.getTeacher().getLastName());

		List <CourseReportAttendance> dtoList = new ArrayList<>();
		for (Attendance attendance : lesson.getAttendances()) {
			dtoList.add(attendanceService.convertToCourseReportAttendance(attendance));
		}
		dto.setAttendanceList(dtoList);
		return dto;
    }
}
