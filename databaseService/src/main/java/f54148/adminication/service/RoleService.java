package f54148.adminication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import f54148.adminication.entity.Role;
import f54148.adminication.repository.RoleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
	
	private final RoleRepository roleRepository;
	
	public List<Role> getRoles() {
		return new ArrayList<>(roleRepository.findAll());
	}

	public Role getRoleById(Long roleId) {
		Optional<Role> opRole = roleRepository.findById(roleId);
		return opRole.orElse(null);
	}

	public boolean addRole(Role role) {
		roleRepository.save(role);
		return true;
	}

	public void updateRole(Role role) {
		roleRepository.save(role);
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

	public Role getRoleByName(String roleName) {
		return roleRepository.findByName(roleName);
	}

	
	
}
