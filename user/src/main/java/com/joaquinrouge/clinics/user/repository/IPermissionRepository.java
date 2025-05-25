package com.joaquinrouge.clinics.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.clinics.user.model.Permission;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long>{
	
	Optional<Permission> findByPermission(String permission);
	boolean existsByPermission(String permission);
}
