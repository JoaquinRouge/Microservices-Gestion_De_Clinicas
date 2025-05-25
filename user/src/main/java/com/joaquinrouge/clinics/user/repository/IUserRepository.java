package com.joaquinrouge.clinics.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.clinics.user.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
