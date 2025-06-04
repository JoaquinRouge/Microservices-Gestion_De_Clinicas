package com.joaquinrouge.clinics.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joaquinrouge.clinics.user.model.Role;
import com.joaquinrouge.clinics.user.model.User;
import com.joaquinrouge.clinics.user.repository.IRoleRepository;
import com.joaquinrouge.clinics.user.repository.IUserRepository;

@Service
public class UserService implements IUserService{

	private final IUserRepository userRepo;
	private final PasswordEncoder encoder;
	private final IRoleRepository roleRepo;
	
	public UserService(IUserRepository userRepo,PasswordEncoder encoder,
			IRoleRepository roleRepo) {
		this.userRepo = userRepo;
		this.encoder = encoder;
		this.roleRepo = roleRepo;
	}
	
	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}

	@Override
	public User findById(Long id) {
		return userRepo.findById(id).orElseThrow(()->
		new IllegalArgumentException("User not found"));
	}

	@Override
	public User findByUsername(String username) {
		return userRepo.findByUsername(username).orElseThrow(()->
		new IllegalArgumentException("User not found"));
	}

	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email).orElseThrow(()->
		new IllegalArgumentException("User not found"));
	}

	@Override
	public User createUser(User user) {
		
		if(userRepo.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("Username already taken");
		}
		
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email already taken");
		}
		
		user.setPassword(encode(user.getPassword()));
		
		return userRepo.save(user);
	}

	@Override
	/**
	 * This method disables the account, it does NOT deletes it.
	 * 
	 * @param id User id
	 * 
	 */
	public void deleteUser(Long id) {
		if(!userRepo.existsById(id)) {
			throw new IllegalArgumentException("User not found");
		}
		
		User userFromDb = findById(id);
		
		userFromDb.setEnabled(false);
		
		userRepo.save(userFromDb);
	}

	@Override
	public User updateUser(User user) {

		User userFromDb = findById(user.getId());
		
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email already taken");
		}
		
		if(userRepo.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("Username already taken");
		}
		
		userFromDb.setEmail(user.getEmail());
		userFromDb.setUsername(user.getUsername());
		
		return userRepo.save(userFromDb);
	}
	
	@Override
	public List<Role> addRoles(Long id, Set<Role> roles) {
	    User user = findById(id);

	    Set<Role> userRoles = user.getRoles();

	    for (Role role : roles) {
	    	
	    	if(!roleRepo.existsByRole(role.getRole())) {
	    		throw new IllegalArgumentException("Role " + role.getRole() + " not found");
	    	}
	    	
	        if (!userRoles.contains(role)) {
	            userRoles.add(role);
	        }
	    }

	    user.setRoles(userRoles);
	    userRepo.save(user);

	    return new ArrayList<Role>(userRoles);
	}


	private String encode(String rawPassword) {
		return encoder.encode(rawPassword);
	}

	
}
