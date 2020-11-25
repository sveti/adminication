package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Course;
import f54148.adminication.entity.File;
import f54148.adminication.entity.Teacher;
import f54148.adminication.repository.FileRepository;

@Service
public class FileService {

	private final FileRepository fileRepository;


	private final TeacherService teacherService;


	private final CourseService courseService;
	
	

	public FileService(FileRepository fileRepository,@Lazy TeacherService teacherService, @Lazy CourseService courseService) {
		super();
		this.fileRepository = fileRepository;
		this.teacherService = teacherService;
		this.courseService = courseService;
	}

	public List<File> getFiles() {
		List<File> fileList = new ArrayList<>();
		fileRepository.findAll().forEach(fileList::add);
		return fileList;
	}

	public File getFileById(Long fileId) {
		Optional<File> opFile = fileRepository.findById(fileId);
		if (opFile.isPresent()) {
			return opFile.get();
		} else {
			return null;
		}
	}

	public boolean addFile(File file) {
		if (fileRepository.save(file) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateFile(File file) {
		if (fileRepository.save(file) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteFile(Long fileId) {
		Optional<File> opFile = fileRepository.findById(fileId);
		if (opFile.isPresent()) {
			File l = opFile.get();

			Teacher t = l.getTeacher();
			t.getFiles().remove(l);
			teacherService.updateTeacher(t);

			Course c = l.getCourse();
			c.getFiles().remove(l);
			courseService.updateCourse(c);

			fileRepository.deleteById(fileId);
			return true;
		} else {
			return false;
		}
	}

}
