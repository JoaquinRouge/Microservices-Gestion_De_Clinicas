package com.joaquinrouge.clinics.user.service;

import org.springframework.stereotype.Service;

import com.joaquinrouge.clinics.user.model.Permission;
import com.joaquinrouge.clinics.user.repository.IPermissionRepository;

@Service
public class PermissionService implements IPermissionService{

	private final IPermissionRepository permRepo;
	
	public PermissionService(IPermissionRepository permRepo) {
		this.permRepo = permRepo;
	}
	
	@Override
	public Permission findById(Long id) {
		return permRepo.findById(id).orElseThrow(()->
		new IllegalArgumentException("Permission with id " + id + " not found"));
	}

	@Override
	public Permission findByPermission(String permission) {
		return permRepo.findByPermission(permission).orElseThrow(()->
		new IllegalArgumentException("Permission not found"));
	}

	@Override
	public Permission createPermission(Permission permission) {
		
		if(permRepo.existsByPermission(permission.getPermission())) {
			throw new IllegalArgumentException("Permission already exists");
		}
		
		return permRepo.save(permission);
	}

	@Override
	public void deletePermission(Long id) {
		if(!permRepo.existsById(id)) {
			throw new IllegalArgumentException("Permission not found");
		}
		
		permRepo.deleteById(id);
		
	}

	@Override
	public Permission updatePermission(Permission permission) {
		
		if(permRepo.existsByPermission(permission.getPermission())) {
			throw new IllegalArgumentException("Permission already exists");
		}
		
		Permission permissionFromDb = findById(permission.getId());
		
		permissionFromDb.setPermission(permission.getPermission());
		
		return permRepo.save(permissionFromDb);
	}

}
