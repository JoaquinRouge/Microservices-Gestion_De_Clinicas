package com.joaquinrouge.clinics.user.service;

import java.util.List;
import java.util.Set;

import com.joaquinrouge.clinics.user.model.Role;
import com.joaquinrouge.clinics.user.model.User;

public interface IUserService {

	List<User> findAll();
	User findById(Long id);
	User findByUsername(String username);
	User findByEmail(String email);
	User createUser(User user);
	List<Role> addRoles(Long id,Set<Role> roles);
	void deleteUser(Long id);
	User updateUser(User user);
}
