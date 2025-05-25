package com.joaquinrouge.clinics.user.controller;

import com.joaquinrouge.clinics.user.model.Permission;
import com.joaquinrouge.clinics.user.model.Role;
import com.joaquinrouge.clinics.user.service.IRoleService;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Role role = roleService.findById(id);
            return ResponseEntity.ok(role);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<?> getByRole(@PathVariable String role) {
        try {
            Role found = roleService.findByRole(role);
            return ResponseEntity.ok(found);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Role role) {
        try {
            Role created = roleService.createRole(role);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/add/permission/{id}")
    public ResponseEntity<?> addRoles(@PathVariable Long id,@RequestBody Set<Permission> permissions){
    	try {
    		Role role = roleService.addPermissions(id, permissions);
    		return ResponseEntity.ok(role);
    	}catch(IllegalArgumentException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Role role) {
        try {
            Role updated = roleService.updateRole(role);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            roleService.deleteRole(id);
            return ResponseEntity.ok("Role deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

