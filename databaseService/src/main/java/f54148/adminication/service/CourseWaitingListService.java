package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseWaitingList;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.CourseWaitingListRepository;

@Service
public class CourseWaitingListService {
	
	@Autowired
	private CourseWaitingListRepository courseWaitingListRepository;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired 
	private StudentService studentService;

	
	 public List<CourseWaitingList> getCourseWaitingLists() {
		  List<CourseWaitingList> courseWaitingListList = new ArrayList<>();
		  courseWaitingListRepository.findAll().forEach(courseWaitingListList::add);
		  return courseWaitingListList;
		 }

		 public CourseWaitingList getCourseWaitingListById(Long courseWaitingListId) {
		  Optional<CourseWaitingList> opCourseWaitingList = courseWaitingListRepository.findById(courseWaitingListId);
		  if (opCourseWaitingList.isPresent()) {
		   return opCourseWaitingList.get();
		  } else {
		   return null;
		  }
		 }

		 public boolean addCourseWaitingList(CourseWaitingList courseWaitingList) {
		  if (courseWaitingListRepository.save(courseWaitingList) != null) {
		   return true;
		  } else {
		   return false;
		  }
		 }
		 
		 public boolean updateCourseWaitingList(CourseWaitingList courseWaitingList) {
			  if (courseWaitingListRepository.save(courseWaitingList) != null) {
			   return true;
			  } else {
			   return false;
			  }
			 }

		 public boolean deleteCourseWaitingList(Long courseWaitingListId) {
			 Optional<CourseWaitingList> opCourseWaitingList = courseWaitingListRepository.findById(courseWaitingListId);
			  if (opCourseWaitingList.isPresent()) {
				  CourseWaitingList ew = opCourseWaitingList.get();			  
	
				  Student s = ew.getStudent();
				  s.getCourseWaitingList().remove(ew);
				  studentService.updateStudent(s);
				  
				  Course e = ew.getCourse();
				  e.getCourseWaitingList().remove(ew);
				  courseService.updateCourse(e);
				  
				  courseWaitingListRepository.deleteById(courseWaitingListId);
				  
			   return true;
			  } else {
			   return false;
			  }
		 }

		public Student getFirstStudentInQueue(Long courseId) {
			
			//Questionable life choices
			return courseService.getCourseWaitingList(courseId).stream().sorted().findFirst().get().getStudent();
			

		}



}
