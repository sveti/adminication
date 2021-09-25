package f54148.adminication.component;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import f54148.adminication.entity.Role;
import f54148.adminication.repository.RoleRepository;

@Configuration
public class InitialRolesSetter {

	@Bean
    public CommandLineRunner mappingDemo(RoleRepository roleRepository) {
        return args -> {
        	
        	if(roleRepository.findByName("ROLE_ADMIN") == null) {

            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleTeacher = new Role("ROLE_TEACHER");
            Role roleStudent = new Role("ROLE_STUDENT");
            Role roleParent = new Role("ROLE_PARENT");
        
            
            roleRepository.saveAll(Arrays.asList(roleAdmin,roleTeacher,roleStudent,roleParent));
            
            
        	}
        };
    }
}
