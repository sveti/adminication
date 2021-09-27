package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.CourseDetailsDTO;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseDetail;
import f54148.adminication.repository.CourseDetailRepository;


@Service
public class CourseDetailService {


	private final CourseDetailRepository repo;
	private final CourseService courseService;
	private final ModelMapper modelMapper;
	
	

	public CourseDetailService(CourseDetailRepository repo, @Lazy CourseService courseService,@Lazy ModelMapper modelMapper) {
		super();
		this.repo = repo;
		this.courseService = courseService;
		this.modelMapper = modelMapper;
	}

	public List<CourseDetail> getCourseDetails() {
		List<CourseDetail> courseDetailsList = new ArrayList<>();
		repo.findAll().forEach(courseDetailsList::add);
		return courseDetailsList;
	}

	public CourseDetail getCourseDetailsById(Long courseId) {
		Optional<CourseDetail> opcourseDetails = repo.findById(courseId);
		return opcourseDetails.orElse(null);
	}

	public Set<Course> getCourses(Long courseId) {
		Optional<CourseDetail> opcourseDetails = repo.findById(courseId);
		return opcourseDetails.map(CourseDetail::getCourses).orElse(null);
	}

	public boolean addCourseDetail(CourseDetail courseDetail) {
		repo.save(courseDetail);
		return true;
	}

	public void updateCourseDetails(CourseDetail courseDetails) {
		repo.save(courseDetails);
	}

	public boolean deleteCourseDetails(Long courseDetailsId) {
		Optional<CourseDetail> opcourseDetails = repo.findById(courseDetailsId);
		if (opcourseDetails.isPresent()) {
			
			CourseDetail cd = opcourseDetails.get();

				for (Course c : cd.getCourses()) {
					c.getDetails().remove(cd);
					courseService.updateCourse(c);
				}
			
			
			repo.deleteById(cd.getId());
			return true;
		} else {
			return false;
		}
	}
	
	public CourseDetailsDTO convertToCourseDetailsDTO(CourseDetail cd) {
		return modelMapper.map(cd, CourseDetailsDTO.class);
	}

	public List<CourseDetailsDTO> getCourseDetailsDTO() {
		List<CourseDetail> courseDetailsList = this.getCourseDetails();
		List<CourseDetailsDTO> dto = new ArrayList<>();
		for(CourseDetail cd : courseDetailsList) {
			dto.add(convertToCourseDetailsDTO(cd));
		}
		return dto;
	}

	public CourseDetail createNewDetail(String newDetail) {
		
		CourseDetail courseDetail = new CourseDetail();
		courseDetail.setDescription(newDetail);
		return repo.save(courseDetail);
	}

	public void removeDetailFromCourse(CourseDetail cs, Course c) {
		
		//many to many 
		cs.getCourses().remove(c);
		updateCourseDetails(cs);
		c.getDetails().remove(cs);
		courseService.updateCourse(c);
		if(cs.getCourses().size() == 0) {
				
			repo.deleteById(cs.getId());
		}
		
	}


	public void addDetailToCourse(CourseDetail cs, Course c) {
		cs.getCourses().add(c);
		updateCourseDetails(cs);
		c.getDetails().add(cs);
		courseService.updateCourse(c);
	}
}
