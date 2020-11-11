package f54148.adminication.controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import f54148.adminication.entity.Privilege;
import f54148.adminication.entity.Role;
import f54148.adminication.service.RoleService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

	 private final RoleService roleService;
	 
	 	@PostMapping("/addRole")
	    public @ResponseBody String createRole(@RequestBody Role role) {
		 if (roleService.addRole(role)) {
				return "Saved role";
			} else {
				return "A problem has occured";
			}
	    }

		@PutMapping(path = "/updateRole")
			public @ResponseBody String updateRole(@RequestBody Role role) {

				if (roleService.addRole(role)) {
					return "Updated role";
				} else {
					return "A problem has occured";
				}
			}
		
		@GetMapping(path = "/roles")
		public @ResponseBody List<Role> getAllRoles() {
			return roleService.getRoles();
		}

		@GetMapping(path = "/role/{id}")
		public @ResponseBody Role getRole(@PathVariable("id") Long id) {
			return roleService.getRoleById(id);
		}
		
		@GetMapping(path = "/role/{id}/privileges")
		public @ResponseBody Set<Privilege> getRolePrivileges(@PathVariable("id") Long id) {
			return roleService.getRolePrivileges(id);
		}
}
