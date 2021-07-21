package f54148.adminication.service.implementations;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import f54148.adminication.dto.AddCourseDTO;
import f54148.adminication.service.CourseService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class CourseServiceImplementation implements CourseService {
	 private final RestTemplate restTemplate;

	@Override
	public String add(AddCourseDTO course) {
		
		System.out.println(course);
		
		return course.toString();
	}
}
