package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Enrollment;
import f54148.adminication.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

	@Autowired
	private EnrollmentRepository enrollmentRepository;
	
	public List<Enrollment> getEnrollments() {
		  List<Enrollment> enrollmentList = new ArrayList<>();
		  enrollmentRepository.findAll().forEach(enrollmentList::add);
		  return enrollmentList;
		 }

		 public Enrollment getEnrollmentById(Long enrollmentId) {
		  Optional<Enrollment> opEnrollment = enrollmentRepository.findById(enrollmentId);
		  if (opEnrollment.isPresent()) {
		   return opEnrollment.get();
		  } else {
		   return null;
		  }
		 }

		 public boolean addEnrollment(Enrollment enrollment) {
		  if (enrollmentRepository.save(enrollment) != null) {
		   return true;
		  } else {
		   return false;
		  }
		 }
		 
		 public boolean updateEnrollment(Enrollment enrollment) {
			  if (enrollmentRepository.save(enrollment) != null) {
			   return true;
			  } else {
			   return false;
			  }
			 }

		 public boolean deleteEnrollment(Long enrollmentId) {
		  if (enrollmentRepository.findById(enrollmentId) != null) {
			  enrollmentRepository.deleteById(enrollmentId);
		   return true;
		  } else {
		   return false;
		  }
		 }
	

	
	
	
}
