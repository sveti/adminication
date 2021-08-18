package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

import f54148.adminication.dto.AddParentDTO;
import f54148.adminication.dto.AddStudentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.StudentOfParentDTO;
import f54148.adminication.service.ParentService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class ParentServiceImplementation implements ParentService {
	
	 private final RestTemplate restTemplate;

	@Override
	public List<StudentOfParentDTO> getStudentOfParentDTO(Long parentId) {
		StudentOfParentDTO[] users = restTemplate.getForObject("http://databaseService/parents/{parentId}/students",StudentOfParentDTO[].class,parentId);
		assert users != null;
		return Arrays.asList(users);
	}

	@Override
	public String addParent(AddParentDTO addParentDTO) {

		ResponseEntity<String> response = restTemplate.postForEntity("http://databaseService/parents/add", addParentDTO, String.class);

		for(AddStudentDTO studentOfParentDTO : addParentDTO.getStudents()){
			restTemplate.postForEntity("http://keycloakadminserver/add", studentOfParentDTO, String.class);
		}
		restTemplate.postForEntity("http://keycloakadminserver/add", addParentDTO, String.class);

		return response.getBody();
	}

}
