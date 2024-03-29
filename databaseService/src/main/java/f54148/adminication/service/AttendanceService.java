package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import f54148.adminication.dto.CourseReportAttendance;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.AttendanceDTO;
import f54148.adminication.entity.Attendance;
import f54148.adminication.entity.Lesson;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.AttendanceRepository;

@Service
public class AttendanceService {
	
	private final AttendanceRepository attendanceRepository;

	private final StudentService studentService;

	private final LessonService lessonService;
	
	private final ModelMapper modelMapper;
	

	public AttendanceService(AttendanceRepository attendanceRepository, @Lazy StudentService studentService,
			@Lazy LessonService lessonService, ModelMapper modelMapper) {
		super();
		this.attendanceRepository = attendanceRepository;
		this.studentService = studentService;
		this.lessonService = lessonService;
		this.modelMapper = modelMapper;
	}

	public List<Attendance> getAttendances() {
		List<Attendance> attendanceList = new ArrayList<>();
		attendanceRepository.findAll().forEach(attendanceList::add);
		return attendanceList;
	}

	public Attendance getAttendanceById(Long attendanceId) {
		Optional<Attendance> opAttendance = attendanceRepository.findById(attendanceId);
		return opAttendance.orElse(null);
	}

	public boolean addAttendance(Attendance attendance) {
		attendanceRepository.save(attendance);
		return true;
	}

	public boolean updateAttendance(Attendance attendance) {
		attendanceRepository.save(attendance);
		return true;
	}

	public boolean deleteAttendance(Long attendanceId) {
		Optional<Attendance> opAttendance = attendanceRepository.findById(attendanceId);
		if (opAttendance.isPresent()) {
			Attendance a = opAttendance.get();

			Student s = a.getStudent();
			s.getAttendances().remove(a);
			studentService.updateStudent(s);

			Lesson l = a.getLesson();
			l.getAttendances().remove(a);
			lessonService.updateLesson(l);
			
			attendanceRepository.deleteById(attendanceId);
			return true;
		} else {
			return false;
		}
	}
	
	public Attendance convertToAttendance(AttendanceDTO attDTO) {
		return modelMapper.map(attDTO, Attendance.class);
		
	
	}
	
	public boolean updateAttendaces(List<AttendanceDTO> attendaceDto) {
		List <Attendance> att = new ArrayList<>();
		
		for(AttendanceDTO dto: attendaceDto) {
			
			Attendance a = getAttendanceById(dto.getId());
			a.setAttended(dto.getAttended());
			
			att.add(a);
		
		}
		
		try {
			for(Attendance a: att) {
				updateAttendance(a);
			}
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
		
	}
	
	public boolean addAttendances(List<AttendanceDTO> attendaceDto) {
		List <Attendance> att = new ArrayList<>();
		
		for(AttendanceDTO dto: attendaceDto) {
			
			att.add(convertToAttendance(dto));
		}
		
		try {
			for(Attendance a: att) {
				addAttendance(a);
			}
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
		
	}

    public CourseReportAttendance convertToCourseReportAttendance(Attendance attendance) {

		CourseReportAttendance dto = new CourseReportAttendance();
		dto.setAttendanceId(attendance.getId());
		dto.setStudentId(attendance.getStudent().getId());
		dto.setStudentName(attendance.getStudent().getName() + " " + attendance.getStudent().getLastName());
		dto.setAttended(attendance.getAttended());
		return dto;
    }
}
