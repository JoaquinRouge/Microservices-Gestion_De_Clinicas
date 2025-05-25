package com.joaquinrouge.clinics.user.service;

import com.joaquinrouge.clinics.user.model.Role;

public interface IRoleService {
	
	Role findById(Long id);
	Role findByRole(String role);
	Role createRole(Role role);
	void deleteRole(Long id);
	Role updateRole(Role role);
	
}
