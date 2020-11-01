package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import f54148.adminication.entity.Parent;
import f54148.adminication.entity.Student;
import f54148.adminication.repository.ParentRepository;

@Service
public class ParentService {
	@Autowired
	private ParentRepository parentRepository;

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

	public List<Student> getChildrenByParentId(Long parentId) {
		Optional<Parent> opParent = parentRepository.findById(parentId);
		if (opParent.isPresent()) {
			return opParent.get().getChildren();
		} else {
			return null;
		}
	}

}
