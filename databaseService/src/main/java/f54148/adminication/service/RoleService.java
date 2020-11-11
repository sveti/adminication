package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import f54148.adminication.entity.Privilege;
import f54148.adminication.entity.Role;
import f54148.adminication.repository.RoleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
	
	private final RoleRepository roleRepository;
	
	public List<Role> getRoles() {
		List<Role> roleList = new ArrayList<>();
		roleRepository.findAll().forEach(roleList::add);
		return roleList;
	}

	public Role getRoleById(Long roleId) {
		Optional<Role> opRole = roleRepository.findById(roleId);
		if (opRole.isPresent()) {
			return opRole.get();
		} else {
			return null;
		}
	}

	public boolean addRole(Role role) {
		if (roleRepository.save(role) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateRole(Role role) {
		if (roleRepository.save(role) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteRole(Long roleId) {
		Optional<Role> opRole = roleRepository.findById(roleId);
		if (opRole.isPresent()) {
			roleRepository.deleteById(roleId);
			return true;
		} else {
			return false;
		}
	}

	public Set<Privilege> getRolePrivileges(Long roleId) {
		Optional<Role> opRole = roleRepository.findById(roleId);
		if (opRole.isPresent()) {
			return opRole.get().getPrivileges();
		} else {
			return null;
		}
	}

	
	
}
