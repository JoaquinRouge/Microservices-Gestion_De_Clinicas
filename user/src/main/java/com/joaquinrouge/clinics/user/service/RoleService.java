package com.joaquinrouge.clinics.user.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.joaquinrouge.clinics.user.model.Permission;
import com.joaquinrouge.clinics.user.model.Role;
import com.joaquinrouge.clinics.user.service.IPermissionService;
import com.joaquinrouge.clinics.user.repository.IRoleRepository;

@Service
public class RoleService implements IRoleService {

	private final IRoleRepository roleRepo;
	private final IPermissionService permService;
	
	public RoleService(IRoleRepository roleRepo,IPermissionService permService) {
		this.roleRepo = roleRepo;
		this.permService = permService;
	}
	
	@Override
	public Role findById(Long id) {
		return roleRepo.findById(id).orElseThrow(()->
		new IllegalArgumentException("Role not found"));
	}

	@Override
	public Role findByRole(String role) {
		return roleRepo.findByRole(role).orElseThrow(()->
		new IllegalArgumentException("Role not found"));
	}

	@Override
	public Role createRole(Role role) {
		
		if(roleRepo.existsByRole(role.getRole())) {
			throw new IllegalArgumentException("Role not found");
		}
		
		return roleRepo.save(role);
	}

	@Override
	public void deleteRole(Long id) {
		if(!roleRepo.existsById(id)) {
			throw new IllegalArgumentException("Role not found");
		}
		
		roleRepo.deleteById(id);
	}

	@Override
	public Role updateRole(Role role) {
		
		Role roleFromDb = findById(role.getId());
		
		if(roleRepo.existsByRole(role.getRole())) {
			throw new IllegalArgumentException("Role already exists");
		}
		
		roleFromDb.setRole(role.getRole());
		
		return roleRepo.save(roleFromDb);
	}

	@Override
	public Role addPermissions(Long id, Set<Permission> permissions) {
		
		Role role  = findById(id);
		
		Set<Permission> rolePermissions = role.getPermissions();
		
		for(Permission p : permissions) {
			
			//Throws IllArgException that has to be catched in Role Controller
			Permission permission = permService.findById(p.getId());
			
			rolePermissions.add(p);
			
		}
		
		role.setPermissions(rolePermissions);
		
		return roleRepo.save(role);
	}


}
