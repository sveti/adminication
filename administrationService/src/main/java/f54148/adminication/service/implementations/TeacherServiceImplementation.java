package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.CreateUserDTO;
import f54148.adminication.service.TeacherService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class TeacherServiceImplementation implements TeacherService {
	
	 private final RestTemplate restTemplate;
		
	 private final PasswordEncoder encoder  = new BCryptPasswordEncoder();

	@Override
	public List<CreateUserDTO> getTeachers() {
		CreateUserDTO teachers[] = restTemplate.getForObject("http://databaseService/teachers/teachers",CreateUserDTO[].class);
		return Arrays.asList(teachers);
	}

	@Override
	public CreateUserDTO getTeacher(@Min(1) long id) {
		CreateUserDTO user = restTemplate.getForObject("http://databaseService/teachers/teacher/{id}",CreateUserDTO.class, id);
		return user;
	}

	@Override
	public String createTeacher(CreateUserDTO teacherDTO) {
		
		teacherDTO.setPassword(encoder.encode(teacherDTO.getPassword()));
		
		String result = restTemplate.postForObject("http://databaseService/teachers/add", teacherDTO, String.class);
		return result;
	};

}
