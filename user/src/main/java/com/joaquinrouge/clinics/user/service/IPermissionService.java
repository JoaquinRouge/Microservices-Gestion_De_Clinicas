package com.joaquinrouge.clinics.user.service;

import com.joaquinrouge.clinics.user.model.Permission;

public interface IPermissionService {

	Permission findById(Long id);
	Permission findByPermission(String permission);
	Permission createPermission(Permission permission);
	void deletePermission(Long id);
	Permission updatePermission(Permission permission);
	
}
