package f54148.adminication.component;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import f54148.adminication.dto.CreateUserDTO;
import f54148.adminication.entity.Gender;
import f54148.adminication.entity.Role;
import f54148.adminication.entity.Teacher;
import f54148.adminication.entity.User;
import f54148.adminication.service.RoleService;

@Configuration
public class ModelMapperConfig {
	
	@Autowired
	RoleService roleService;
	
	 @Bean
	    public ModelMapper modelMapper() {
		 	ModelMapper modelMapper = new ModelMapper();
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
	
	    
}

