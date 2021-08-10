package f54148.adminication.controller;

import java.util.HashSet;
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
import f54148.adminication.service.PrivilegeService;
import f54148.adminication.service.RoleService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/privileges")
public class PrivilegeController {

	private final PrivilegeService privilegeService;
	
	private final RoleService roleService;
	 
	 @PostMapping("/addPrivilege")
	    public @ResponseBody String createPrivilege(@RequestBody Privilege privilege) {
		 
		 Set<Role> roles = privilege.getRoles();
		 Set<Role> updatedWithPrivilege = new HashSet<>();
		 
		 privilege.setRoles(new HashSet<>());
		 privilegeService.addPrivilege(privilege);
		 
		 for(Role r: roles) {
			 r.getPrivileges().add(privilege);
			 roleService.updateRole(r);
			 updatedWithPrivilege.add(r);
		 }
		 
		 privilege.setRoles(updatedWithPrivilege);
		 
		 if (privilegeService.updatePrivilege(privilege)) {
				return "Saved privilege";
			} else {
				return "A problem has occured";
			}
	    }

		@PutMapping(path = "/updatePrivilege")
			public @ResponseBody String updatePrivilege(@RequestBody Privilege privilege) {

				if (privilegeService.addPrivilege(privilege)) {
					return "Updated privilege";
				} else {
					return "A problem has occured";
				}
			}
		
		@GetMapping(path = "/privileges")
		public @ResponseBody List<Privilege> getAllPrivileges() {
			return privilegeService.getPrivileges();
		}

		@GetMapping(path = "/privilege/{id}")
		public @ResponseBody Privilege getPrivilege(@PathVariable("id") Long id) {
			return privilegeService.getPrivilegeById(id);
		}
}
