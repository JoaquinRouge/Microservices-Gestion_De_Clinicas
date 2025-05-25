package com.joaquinrouge.clinics.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.clinics.user.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByRole(String role);
	boolean existsByRole(String role);
}
