package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.AddCourseWaitingListSignUpDTO;
import f54148.adminication.dto.CourseOfStudentInWaitingListDTO;
import f54148.adminication.entity.Course;
import f54148.adminication.entity.CourseWaitingList;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.CourseWaitingListRepository;

@Service
public class CourseWaitingListService {

	private final CourseWaitingListRepository courseWaitingListRepository;

	private final CourseService courseService;
	private final DraftService draftService;
	private final NotificationService notificationService;
	private final StudentService studentService;
	
	

	public CourseWaitingListService(CourseWaitingListRepository courseWaitingListRepository,
			@Lazy CourseService courseService,@Lazy StudentService studentService,@Lazy DraftService draftService,@Lazy NotificationService notificationService) {
		super();
		this.courseWaitingListRepository = courseWaitingListRepository;
		this.courseService = courseService;
		this.studentService = studentService;
		this.draftService = draftService;
		this.notificationService = notificationService;
	}

	public List<CourseWaitingList> getCourseWaitingLists() {
		List<CourseWaitingList> courseWaitingListList = new ArrayList<>();
		courseWaitingListRepository.findAll().forEach(courseWaitingListList::add);
		return courseWaitingListList;
	}

	public CourseWaitingList getCourseWaitingListById(Long courseWaitingListId) {
		Optional<CourseWaitingList> opCourseWaitingList = courseWaitingListRepository.findById(courseWaitingListId);
		return opCourseWaitingList.orElse(null);
	}

	public boolean addCourseWaitingList(CourseWaitingList courseWaitingList) {
		courseWaitingListRepository.save(courseWaitingList);
		return true;
	}
	
	public boolean addWaitingListSignUp(AddCourseWaitingListSignUpDTO dto) {
		CourseWaitingList courseWaitingList = new CourseWaitingList();
		courseWaitingList.setStudent(studentService.getStudentById(dto.getStudentId()));
		courseWaitingList.setCourse(courseService.getCourseById(dto.getCourseId()));
		courseWaitingList.setSigned(dto.getSigned());
		if(addCourseWaitingList(courseWaitingList)){
			String message = courseWaitingList.getStudent().getName() + " " + courseWaitingList.getStudent().getLastName() + " has been successfully been added to course waiting list for course #" + courseWaitingList.getCourse().getId() + " : " + courseWaitingList.getCourse().getTitle();
			Long draftId = draftService.createDraftFromAdmin(message);
			Long parentId = courseWaitingList.getStudent().getParent().getId();
			String studentMessage = "You been successfully been added to course waiting list for course #" + courseWaitingList.getCourse().getId() + " : " + courseWaitingList.getCourse().getTitle();
			notificationService.sendDraft(draftId, parentId);
			Long studentDraftId = draftService.createDraftFromAdmin(studentMessage);
			notificationService.sendDraft(studentDraftId, courseWaitingList.getStudent().getId());
			return true;
		}
		return false;
	}

	public boolean updateCourseWaitingList(CourseWaitingList courseWaitingList) {
		courseWaitingListRepository.save(courseWaitingList);
		return true;
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

		return courseService.getCourseWaitingList(courseId).stream().sorted().findFirst().get().getStudent();

	}
	
	public CourseWaitingList getFirstCourseWaitingListInQueue(Long courseId) {
		return courseService.getCourseWaitingList(courseId).stream().sorted().findFirst().get();
	}
	
	public Integer getNumberOfStudentInQueue(CourseWaitingList cw) {
		ArrayList<CourseWaitingList> waitingLists = (ArrayList<CourseWaitingList>) cw.getCourse().getCourseWaitingList().stream().sorted().collect(Collectors.toList());
		return waitingLists.indexOf(cw);
		
	}

	public Student getStudentByCourseWaitingListId(Long courseWaitingListId) {
		Optional<CourseWaitingList> opCourseWaitingList = courseWaitingListRepository.findById(courseWaitingListId);
		return opCourseWaitingList.map(CourseWaitingList::getStudent).orElse(null);
	}

	public Course getCourseByCourseWaitingListId(Long courseWaitingListId) {
		Optional<CourseWaitingList> opCourseWaitingList = courseWaitingListRepository.findById(courseWaitingListId);
		return opCourseWaitingList.map(CourseWaitingList::getCourse).orElse(null);
	}
	
	public List<CourseOfStudentInWaitingListDTO> getWaitingListsOfStudent(Long studentId){
		
		Set<CourseWaitingList> courseWaitingList = studentService.getStudentById(studentId).getCourseWaitingList();
		List<CourseOfStudentInWaitingListDTO> dtoList = new ArrayList<>();
		
		for(CourseWaitingList cw: courseWaitingList) {
			CourseOfStudentInWaitingListDTO dto = new CourseOfStudentInWaitingListDTO();
			dto.setCourseWaitingListId(cw.getId());
			dto.setCourseId(cw.getCourse().getId());
			dto.setStudentId(cw.getStudent().getId());
			dto.setNumberInLine(getNumberOfStudentInQueue(cw));
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	
}
