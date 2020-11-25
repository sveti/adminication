package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Parent;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.ParentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParentService {

	private final ParentRepository parentRepository;
	private final PasswordEncoder encoder  = new BCryptPasswordEncoder();

	public List<Parent> getParents() {
		List<Parent> parentsList = new ArrayList<>();
		parentRepository.findAll().forEach(parentsList::add);
		return parentsList;
	}

	public Parent getParentById(Long parentId) {
		Optional<Parent> opParent = parentRepository.findById(parentId);
		if (opParent.isPresent()) {
			return opParent.get();
		} else {
			return null;
		}
	}

	public boolean addParent(Parent parent) {
		
		parent.setPassword(encoder.encode(parent.getPassword()));
		
		if (parentRepository.save(parent) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateParent(Parent parent) {
		if (parentRepository.save(parent) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteParent(Long parentId) {
		if (parentRepository.findById(parentId) != null) {
			parentRepository.deleteById(parentId);
			return true;
		} else {
			return false;
		}
	}

	public Set<Student> getChildrenByParentId(Long parentId) {
		Optional<Parent> opParent = parentRepository.findById(parentId);
		if (opParent.isPresent()) {
			return opParent.get().getChildren();
		} else {
			return null;
		}
	}
	
	public String encodePassword(String pass) {
		
		return encoder.encode(pass);
		
	}


}
