package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import f54148.adminication.dto.StudentOfParentDTO;
import f54148.adminication.entity.Parent;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.ParentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParentService {

	private final ParentRepository parentRepository;
	private final ModelMapper modelMapper;
	//private final PasswordEncoder encoder  = new BCryptPasswordEncoder();

	public List<Parent> getParents() {
		List<Parent> parentsList = new ArrayList<>();
		parentRepository.findAll().forEach(parentsList::add);
		return parentsList;
	}

	public Parent getParentById(Long parentId) {
		Optional<Parent> opParent = parentRepository.findById(parentId);
		return opParent.orElse(null);
	}

	public boolean addParent(Parent parent) {
		
		//parent.setPassword(encoder.encode(parent.getPassword()));

		parentRepository.save(parent);
		return true;
	}

	public boolean updateParent(Parent parent) {
		parentRepository.save(parent);
		return true;
	}

	public boolean deleteParent(Long parentId) {
		if (parentRepository.findById(parentId).isPresent()) {
			parentRepository.deleteById(parentId);
			return true;
		} else {
			return false;
		}
	}

	public Set<Student> getChildrenByParentId(Long parentId) {
		Optional<Parent> opParent = parentRepository.findById(parentId);
		return opParent.map(Parent::getChildren).orElse(null);
	}
	
	public List<StudentOfParentDTO> getStudentOfParentDTO(Long parentId){
		Set<Student> students = getChildrenByParentId(parentId);
		List<StudentOfParentDTO> dtos = new ArrayList<>();
		for(Student s: students) {
			dtos.add(modelMapper.map(s, StudentOfParentDTO.class));
		}
		return dtos;
	}


}
