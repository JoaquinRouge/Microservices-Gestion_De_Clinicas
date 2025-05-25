package com.joaquinrouge.clinics.user.service;

import java.util.Set;

import com.joaquinrouge.clinics.user.model.Permission;
import com.joaquinrouge.clinics.user.model.Role;

public interface IRoleService {
	
	Role findById(Long id);
	Role findByRole(String role);
	Role createRole(Role role);
	void deleteRole(Long id);
	Role updateRole(Role role);
	Role addPermissions(Long id,Set<Permission> permissions);
}
