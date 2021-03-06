package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseDetail;
import f54148.adminication.repository.CourseDetailRepository;


@Service
public class CourseDetailService {


	private final CourseDetailRepository repo;
	private final CourseService courseService;
	
	

	public CourseDetailService(CourseDetailRepository repo, @Lazy CourseService courseService) {
		super();
		this.repo = repo;
		this.courseService = courseService;
	}

	public List<CourseDetail> getCourseDetails() {
		List<CourseDetail> courseDetailsList = new ArrayList<>();
		repo.findAll().forEach(courseDetailsList::add);
		return courseDetailsList;
	}

	public CourseDetail getCourseDetailsById(Long courseId) {
		Optional<CourseDetail> opcourseDetails = repo.findById(courseId);
		if (opcourseDetails.isPresent()) {
			return opcourseDetails.get();
		} else {
			return null;
		}
	}

	public Set<Course> getCourses(Long courseId) {
		Optional<CourseDetail> opcourseDetails = repo.findById(courseId);
		if (opcourseDetails.isPresent()) {
			return opcourseDetails.get().getCourses();
		} else {
			return null;
		}
	}

	public boolean addCourseDetail(CourseDetail courseDetail) {
		if (repo.save(courseDetail) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateCourseDetails(CourseDetail courseDetails) {
		if (repo.save(courseDetails) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteCourseDetails(Long courseDetailsId) {
		Optional<CourseDetail> opcourseDetails = repo.findById(courseDetailsId);
		if (opcourseDetails.isPresent()) {
			CourseDetail cd = opcourseDetails.get();

			for (Course c : cd.getCourses()) {
				c.getDetails().remove(cd);
				courseService.updateCourse(c);
			}

			repo.deleteById(courseDetailsId);

			return true;
		} else {
			return false;
		}
	}

}
