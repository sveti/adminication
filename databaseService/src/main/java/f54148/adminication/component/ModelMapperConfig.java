package f54148.adminication.component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import f54148.adminication.dto.CourseWithDetailsDTO;
import f54148.adminication.dto.DisplayUserDTO;
import f54148.adminication.dto.FinshedCourseDTO;
import f54148.adminication.dto.StartedCourseDTO;
import f54148.adminication.dto.StudentGradesDTO;
import f54148.adminication.dto.UpcommingCourseDTO;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseDetail;
import f54148.adminication.entity.Enrollment;
import f54148.adminication.entity.Gender;
import f54148.adminication.entity.Schedule;
import f54148.adminication.entity.Teacher;
import f54148.adminication.entity.User;
import f54148.adminication.service.CourseService;

@Configuration
public class ModelMapperConfig {
	
	private final CourseService courseService;
	
	
	 public ModelMapperConfig(@Lazy CourseService courseService) {
		super();
		this.courseService = courseService;
	}

	@Bean
	    public ModelMapper modelMapper() {
		 	ModelMapper modelMapper = new ModelMapper();
		 	
		 	modelMapper.addMappings(convertUserToCreateUserDTO);
		 	
		 	modelMapper.addConverter(convertCoursetoUpcommingCourseDTO);
		 	modelMapper.addConverter(convertCoursetoCourseWithDetailsDTO);
		 	modelMapper.addConverter(convertCoursetoStartedCourseDTO);
		 	modelMapper.addConverter(convertCoursetoFinshedCourseDTO);
		 	modelMapper.addConverter(convertEnrollmenttoStudentGradesDTO);
		 	
		 	return modelMapper;
	    }
	    
	    PropertyMap<User, DisplayUserDTO> convertUserToCreateUserDTO = new PropertyMap<User, DisplayUserDTO>()
	    {
		 protected void configure() {
				if(source.getGender()==Gender.MALE) {
					map().setGender("MALE");
			 	}
			 	else {
			 		map().setGender("FEMALE");
			 	}
			    
			  }
	    };

	    Converter<Course, UpcommingCourseDTO> convertCoursetoUpcommingCourseDTO = new Converter<Course, UpcommingCourseDTO>()
	    {
	        public UpcommingCourseDTO convert(MappingContext<Course, UpcommingCourseDTO> context)
	        {

	        	Course source = context.getSource();
	        	UpcommingCourseDTO destination = new UpcommingCourseDTO();
	        	
	        	destination.setId(source.getId());
	        	destination.setTitle(source.getTitle());
	        	
	        	List<LocalTime> startTimes = new ArrayList<LocalTime>();
	        	List<LocalTime> endTimes = new ArrayList<LocalTime>();
	        	List<LocalDate> startDates = new ArrayList<LocalDate>();
	        	
	        	for(Schedule s: source.getCourseSchedule()){
	        		startTimes.add(s.getStartTime());
	        		endTimes.add(s.getEndTime());
	        		startDates.add(s.getStartDate());
	        	}
	        	
	        	destination.setStartTime(startTimes);
	        	destination.setEndTime(endTimes);
	        	destination.setStartDate(startDates);
	        	
	        	destination.setDuration(source.getDuration());
	        	destination.setSignUpLimit(source.getMaxStudents());
	        	destination.setSignedUp(source.getEnrollments().size());
	        	
	            return destination;
	        }
	    };
	    
	    
	    Converter<Course, CourseWithDetailsDTO> convertCoursetoCourseWithDetailsDTO = new Converter<Course, CourseWithDetailsDTO>()
	    {
	        public CourseWithDetailsDTO convert(MappingContext<Course, CourseWithDetailsDTO> context)
	        {

	        	Course source = context.getSource();
	        	CourseWithDetailsDTO destination = new CourseWithDetailsDTO();
	        	
	        	destination.setId(source.getId());
	        	destination.setTitle(source.getTitle());
	        	
	        	List<LocalTime> startTimes = new ArrayList<LocalTime>();
	        	List<LocalTime> endTimes = new ArrayList<LocalTime>();
	        	List<LocalDate> startDates = new ArrayList<LocalDate>();
	        	List<String> courseDetails = new ArrayList<String>();
	        	List<String> teachers = new ArrayList<String>();
	        	
	        	for(Schedule s: source.getCourseSchedule()){
	        		startTimes.add(s.getStartTime());
	        		endTimes.add(s.getEndTime());
	        		startDates.add(s.getStartDate());
	        	}
	        	
	        	for(CourseDetail cd: source.getDetails()) {
	        		courseDetails.add(cd.getDescription());
	        	}
	        	
	        	for(Teacher t: courseService.getTeachersByCourseId(source.getId())) {
	        		teachers.add(t.getName()+" "+t.getLastName());
	        	}
	        	
	        	destination.setLevel(source.getLevel().name());
	        	
	        	destination.setStartTime(startTimes);
	        	destination.setEndTime(endTimes);
	        	destination.setStartDate(startDates);
	        	
	        	destination.setDuration(source.getDuration());
	        	destination.setSignUpLimit(source.getMaxStudents());
	        	destination.setSignedUp(source.getEnrollments().size());
	        	
	        	destination.setDetails(courseDetails);
	        	destination.setTeachers(teachers);
	        	
	            return destination;
	        }
	    };
	    
	    Converter<Course, StartedCourseDTO> convertCoursetoStartedCourseDTO = new Converter<Course, StartedCourseDTO>()
	    {
	        public StartedCourseDTO convert(MappingContext<Course, StartedCourseDTO> context)
	        {

	        	Course source = context.getSource();
	        	StartedCourseDTO destination = new StartedCourseDTO();
	        	
	        	destination.setId(source.getId());
	        	destination.setTitle(source.getTitle());
	        	
	        	List<LocalTime> startTimes = new ArrayList<LocalTime>();
	        	List<LocalTime> endTimes = new ArrayList<LocalTime>();
	        	List<LocalDate> startDates = new ArrayList<LocalDate>();
	        	
	        	for(Schedule s: source.getCourseSchedule()){
	        		startTimes.add(s.getStartTime());
	        		endTimes.add(s.getEndTime());
	        		startDates.add(s.getStartDate());
	        	}
	        	    	
	        	destination.setStartTime(startTimes);
	        	destination.setEndTime(endTimes);
	        	destination.setStartDate(startDates);

	        	
	            return destination;
	        }
	    };
	    
	    Converter<Course, FinshedCourseDTO> convertCoursetoFinshedCourseDTO = new Converter<Course, FinshedCourseDTO>()
	    {
	        public FinshedCourseDTO convert(MappingContext<Course, FinshedCourseDTO> context)
	        {
	        	
	        	Course source = context.getSource();
	        	FinshedCourseDTO destination = new FinshedCourseDTO();
	        	
	        	destination.setId(source.getId());
	        	destination.setTitle(source.getTitle());
	        	destination.setSignedUp(source.getEnrollments().size());
	        	
	        	int numberOfSetGrades =0;
	        	
	        	for(Enrollment e: source.getEnrollments()) {
	        		if(e.getGrade()>0) {
	        			numberOfSetGrades++;
	        		}
	        	}
	        	
	        	destination.setNumberOfSetGrades(numberOfSetGrades);
	            return destination;
	        }
	    };

	    
	    Converter<Enrollment, StudentGradesDTO> convertEnrollmenttoStudentGradesDTO = new Converter<Enrollment, StudentGradesDTO>()
	    {
	        public StudentGradesDTO convert(MappingContext<Enrollment, StudentGradesDTO> context)
	        {
	        	
	        	Enrollment source = context.getSource();
	        	StudentGradesDTO destination = new StudentGradesDTO();
	        	
	        	destination.setId(source.getId());
	        	destination.setStudentId(source.getStudent().getId());
	        	destination.setCourseId(source.getCourse().getId());
	        	destination.setUsername(source.getStudent().getUsername());
	        	destination.setName(source.getStudent().getName());
	        	destination.setLastName(source.getStudent().getLastName());
	        	destination.setGrade(source.getGrade());
	        	
	            return destination;
	        }
	    };
}

