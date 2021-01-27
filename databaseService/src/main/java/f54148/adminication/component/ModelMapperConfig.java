package f54148.adminication.component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import f54148.adminication.dto.CourseWithDetailsDTO;
import f54148.adminication.dto.CreateUserDTO;
import f54148.adminication.dto.StartedCourseDTO;
import f54148.adminication.dto.UpcommingCourseDTO;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseDetail;
import f54148.adminication.entity.Gender;
import f54148.adminication.entity.Schedule;
import f54148.adminication.entity.Teacher;
import f54148.adminication.service.CourseService;
import f54148.adminication.service.RoleService;

@Configuration
public class ModelMapperConfig {
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	CourseService courseService;
	
	 @Bean
	    public ModelMapper modelMapper() {
		 	ModelMapper modelMapper = new ModelMapper();
		 	modelMapper.addConverter(convertCoursetoUpcommingCourseDTO);
		 	modelMapper.addConverter(convertCoursetoCourseWithDetailsDTO);
		 	modelMapper.addConverter(convertCoursetoStartedCourseDTO);
		 	
		 	modelMapper.typeMap(CreateUserDTO.class, Teacher.class)
            .addMappings(mapper -> mapper.using(convertStringToGender).map(CreateUserDTO::getGender, Teacher::setGender));
		 	modelMapper.typeMap(Teacher.class, CreateUserDTO.class)
            .addMappings(mapper -> mapper.using(convertGenderToString).map(Teacher::getGender, CreateUserDTO::setGender));
	        return modelMapper;
	    }
	 

	Converter<String, Gender> convertStringToGender = new Converter<String, Gender>()
	    {
	        public Gender convert(MappingContext<String, Gender> context)
	        {
	            if(context.getSource() =="MALE") {
	            	return Gender.MALE;
	            }
	            else {
	            	return Gender.FEMALE;
	            }
	        }
	    };
	    
		Converter<Gender, String> convertGenderToString = new Converter<Gender, String>()
	    {
	        public String convert(MappingContext<Gender, String> context)
	        {
	            return context.getSource()==Gender.MALE?"MALE":"FEMALE";
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
}

