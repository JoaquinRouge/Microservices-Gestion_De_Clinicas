package com.joaquinrouge.clinics.user.service;

import org.springframework.stereotype.Service;

import com.joaquinrouge.clinics.user.model.Role;
import com.joaquinrouge.clinics.user.repository.IRoleRepository;

@Service
public class RoleService implements IRoleService {

	private final IRoleRepository roleRepo;
	
	public RoleService(IRoleRepository roleRepo) {
		this.roleRepo = roleRepo;
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

}
