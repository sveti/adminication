package f54148.adminication.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.CourseWithDetailsDTO;
import f54148.adminication.dto.FinshedCourseDTO;
import f54148.adminication.dto.StartedCourseDTO;
import f54148.adminication.dto.StartedCourseStudentDTO;
import f54148.adminication.dto.StudentAttendanceDTO;
import f54148.adminication.dto.StudentLessonDTO;
import f54148.adminication.dto.UpcommingCourseDTO;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseDetail;
import f54148.adminication.entity.CourseStatus;
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
	private final TeacherService teacherService;
	private final StudentService studentService;
	private final LessonService lessonService;
	private final ModelMapper modelMapper;

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
	
	public List<Course> getCoursesByStatusAndTeacherId(CourseStatus cs, Long idTeacher){
		
		List<Course> courses = teacherService.getCoursesByTeacherId(idTeacher);
		
		courses.removeIf(course -> course.getStatus()!=cs);
		return courses;
	}
	
	public List<Course> getCoursesByStatusAndStudentId(CourseStatus cs, Long idStudent){
		
		List<Course> courses = studentService.getCoursesStudentById(idStudent);
		
		courses.removeIf(course -> course.getStatus()!=cs);
		return courses;
	}

	public UpcommingCourseDTO convertToUpcommingCourseDTO(Course course) {
		UpcommingCourseDTO upcomming =  modelMapper.map(course, UpcommingCourseDTO.class);
		return upcomming;
        
    }

	public UpcommingCourseDTO getUpcommingCourseDTOById(Long idCourse) {
		
		return convertToUpcommingCourseDTO( getCourseById(idCourse));
	}

	public List<UpcommingCourseDTO> getUpcommingCoursesDTOByTeacherId(Long idTeacher) {
		
		List<Course> courses = getCoursesByStatusAndTeacherId(CourseStatus.UPCOMMING,idTeacher);
		
		List<UpcommingCourseDTO> upcommingList = new ArrayList<UpcommingCourseDTO>();
		
		for(Course c: courses) {
			
			upcommingList.add(convertToUpcommingCourseDTO(c));

		}
		
		return upcommingList;
	}
	
	public List<UpcommingCourseDTO> getUpcommingCoursesDTOByStudentId(Long idStudent) {
		List<Course> courses = getCoursesByStatusAndStudentId(CourseStatus.UPCOMMING,idStudent);
		
		List<UpcommingCourseDTO> upcommingList = new ArrayList<UpcommingCourseDTO>();
		
		for(Course c: courses) {
			
			upcommingList.add(convertToUpcommingCourseDTO(c));

		}
		
		return upcommingList;
	}

	
	
	public CourseWithDetailsDTO convertToCourseWithDetailsDTO(Course c) {
		CourseWithDetailsDTO courseWithDetails =  modelMapper.map(c, CourseWithDetailsDTO.class);
		return courseWithDetails;
	}
	
	public CourseWithDetailsDTO getCourseWithDetailsDTO(Long idCourse ) {
		
		return convertToCourseWithDetailsDTO( getCourseById(idCourse));
	}
	
	public List<CourseWithDetailsDTO> getAllCourseWithDetailsDTO() {
		List<Course> allCourses = this.getCourses();
		allCourses.removeIf(course -> course.getStatus() == CourseStatus.FINISHED || course.getStatus() == CourseStatus.CANCELED);
		
		List<CourseWithDetailsDTO> dtoList = new ArrayList<>();
		
		for(Course c: allCourses) {
			dtoList.add(convertToCourseWithDetailsDTO(c));
		}
	
		return dtoList;
	}
	
	
	public StartedCourseDTO convertToStartedCourseDTO(Course c) {
		StartedCourseDTO startedCourse =  modelMapper.map(c, StartedCourseDTO.class);
		return startedCourse;
	}
	
	public StartedCourseStudentDTO convertToStartedCourseStudentDTO(Course c,Long studentId) {
		
		StartedCourseDTO dto =  modelMapper.map(c, StartedCourseDTO.class);
		StartedCourseStudentDTO startedCourse = new StartedCourseStudentDTO(dto);
		
		Set<Lesson> lessons = getLessonsByCourseId(c.getId());
		List<StudentLessonDTO> lessonsDTO = new ArrayList<>();
		
		for(Lesson l : lessons) {
			lessonsDTO.add(lessonService.convertToStudentLessonDTO(l, studentId));
		}
		
		startedCourse.setLessons(lessonsDTO);
		
		return startedCourse;
	}
	
	public StartedCourseDTO getStartedCourseDTOById(Long idCourse ) {
		
		return convertToStartedCourseDTO( getCourseById(idCourse));
	}
	
	

	public List<StartedCourseDTO> getStartedCourseDTOByTeacherId(Long idTeacher) {
		List<Course> courses = getCoursesByStatusAndTeacherId(CourseStatus.STARTED,idTeacher);
		
		List<StartedCourseDTO> startedList = new ArrayList<StartedCourseDTO>();
		
		for(Course c: courses) {
			
			startedList.add(convertToStartedCourseDTO(c));
			
		}
		
		return startedList;
	}
	
	public List<StartedCourseStudentDTO> getStartedCourseDTOByStudentId(Long idStudent) {
		
		List<Course> courses = getCoursesByStatusAndStudentId(CourseStatus.STARTED,idStudent);
		
		List<StartedCourseStudentDTO> startedList = new ArrayList<StartedCourseStudentDTO>();
		
		for(Course c: courses) {
			
			startedList.add(convertToStartedCourseStudentDTO(c,idStudent));
			
		}
		
		return startedList;
	}
	
	
	public FinshedCourseDTO convertToFinshedCourseDTO(Course c) {
		FinshedCourseDTO finishedCourse =  modelMapper.map(c, FinshedCourseDTO.class);
		return finishedCourse;
	}
	
	public List<FinshedCourseDTO> getFinshedCourseDTOByTeacherId(Long idTeacher) {
		List<Course> courses = getCoursesByStatusAndTeacherId(CourseStatus.FINISHED,idTeacher);
		
		List<FinshedCourseDTO> finshedList = new ArrayList<FinshedCourseDTO>();
		for(Course c: courses) {
			finshedList.add(convertToFinshedCourseDTO(c));	
		}
		
		return finshedList;
	}

	public List<StartedCourseDTO> getSubStartedCourseDTOByTeacherId(Long idTeacher) {
		List <Course> courses = teacherService.getsubstituteCoursesByTeacherId(idTeacher);
		List<StartedCourseDTO> startedList = new ArrayList<StartedCourseDTO>();
		for(Course c: courses) {
			if(c.getStatus()==CourseStatus.STARTED)
			startedList.add(convertToStartedCourseDTO(c));
			
		}
		return startedList;
	}
	
	public StudentAttendanceDTO convertToStudentAttendanceDTO(Student student) {
		
		StudentAttendanceDTO studentDTO =  modelMapper.map(student, StudentAttendanceDTO.class);
		return studentDTO;
	}
	
	public List<StudentAttendanceDTO> getStudentAttendanceDTOOfLesson(Long courseId){
		List<Student> students = getStudentsByCourseId(courseId);
		List<StudentAttendanceDTO> studentsDTO = new ArrayList<>();
		
		for(Student s: students) {
			studentsDTO.add(convertToStudentAttendanceDTO(s));
		}
		
		return studentsDTO;
		
	}
	
	public List<StudentAttendanceDTO> getStudentAttendanceDTOOfCourse(Long courseId){
		List<Student> students = getStudentsByCourseId(courseId);
		List<StudentAttendanceDTO> studentsDTO = new ArrayList<>();
		
		for(Student s: students) {
			studentsDTO.add(convertToStudentAttendanceDTO(s));
		}
		
		return studentsDTO;
		
	}






	
	
}
