package f54148.adminication.component;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import f54148.adminication.entity.Privilege;
import f54148.adminication.entity.Role;
import f54148.adminication.repository.PrivilegeRepository;
import f54148.adminication.repository.RoleRepository;

@Configuration
public class InitialRolesSetter {

	@Bean
    public CommandLineRunner mappingDemo(RoleRepository roleRepository,
                                         PrivilegeRepository privilegeRepository) {
        return args -> {
        	
        	if(roleRepository.findByName("ROLE_ADMIN") == null) {

            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleTeacher = new Role("ROLE_TEACHER");
            Role roleStudent = new Role("ROLE_STUDENT");
            Role roleParent = new Role("ROLE_PARENT");
        
            
            roleRepository.saveAll(Arrays.asList(roleAdmin,roleTeacher,roleStudent,roleParent));


            Privilege privilegeUsers = new Privilege("/users");
            Privilege privilegeCourse = new Privilege("/courses");
            Privilege privilegeEnrollment = new Privilege("/enrollments");
            Privilege privilegeEvent = new Privilege("/events");
            Privilege privilegeFile = new Privilege("/files");
            Privilege privilegeParent = new Privilege("/parents");
            Privilege privilegeStudent = new Privilege("/students");
            Privilege privilegeTeacher = new Privilege("/teachers");
            Privilege privilegeSchedule = new Privilege("/schedules");
            
            
            privilegeRepository.saveAll(Arrays.asList(privilegeUsers,privilegeCourse,privilegeEnrollment,privilegeEvent,privilegeFile,privilegeParent,privilegeStudent,privilegeTeacher,privilegeSchedule));
                
            roleAdmin.getPrivileges().addAll(Arrays.asList(privilegeUsers,privilegeCourse,privilegeEnrollment,privilegeEvent,privilegeFile,privilegeParent,privilegeStudent,privilegeTeacher,privilegeSchedule));
            roleRepository.save(roleAdmin);
            
            roleTeacher.getPrivileges().addAll(Arrays.asList(privilegeCourse,privilegeEnrollment,privilegeEvent,privilegeFile,privilegeStudent,privilegeTeacher,privilegeSchedule));
            roleRepository.save(roleTeacher);
            
            roleStudent.getPrivileges().addAll(Arrays.asList(privilegeCourse,privilegeEnrollment,privilegeEvent,privilegeFile,privilegeStudent,privilegeSchedule));
            roleRepository.save(roleStudent);
            
            roleParent.getPrivileges().addAll(Arrays.asList(privilegeCourse,privilegeEnrollment,privilegeEvent,privilegeFile,privilegeStudent,privilegeParent,privilegeSchedule));
            roleRepository.save(roleParent);
            
            
        	}
        };
    }
}
