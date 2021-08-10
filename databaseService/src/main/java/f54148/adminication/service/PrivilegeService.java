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
		return new ArrayList<>(privilegeRepository.findAll());
	}

	public Privilege getPrivilegeById(Long privilegeId) {
		Optional<Privilege> opPrivilege = privilegeRepository.findById(privilegeId);
		return opPrivilege.orElse(null);
	}

	public boolean addPrivilege(Privilege privilege) {
		privilegeRepository.save(privilege);
		return true;
	}

	public boolean updatePrivilege(Privilege privilege) {
		privilegeRepository.save(privilege);
		return true;
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
