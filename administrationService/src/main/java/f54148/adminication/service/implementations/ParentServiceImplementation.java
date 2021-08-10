package f54148.adminication.service.implementations;

import java.util.Arrays;
import java.util.List;

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

}
