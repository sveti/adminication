package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import f54148.adminication.entity.Privilege;
import f54148.adminication.repository.PrivilegeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrivilegeService {

private final PrivilegeRepository privilegeRepository;
	
	public List<Privilege> getPrivileges() {
		List<Privilege> privilegeList = new ArrayList<>();
		privilegeRepository.findAll().forEach(privilegeList::add);
		return privilegeList;
	}

	public Privilege getPrivilegeById(Long privilegeId) {
		Optional<Privilege> opPrivilege = privilegeRepository.findById(privilegeId);
		if (opPrivilege.isPresent()) {
			return opPrivilege.get();
		} else {
			return null;
		}
	}

	public boolean addPrivilege(Privilege privilege) {
		if (privilegeRepository.save(privilege) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updatePrivilege(Privilege privilege) {
		if (privilegeRepository.save(privilege) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deletePrivilege(Long privilegeId) {
		Optional<Privilege> opPrivilege = privilegeRepository.findById(privilegeId);
		if (opPrivilege.isPresent()) {
			privilegeRepository.deleteById(privilegeId);
			return true;
		} else {
			return false;
		}
	}
 
	
}
