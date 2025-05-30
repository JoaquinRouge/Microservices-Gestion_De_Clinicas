package com.joaquinrouge.clinics.user.controller;

import com.joaquinrouge.clinics.user.model.Permission;
import com.joaquinrouge.clinics.user.service.IPermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    private final IPermissionService permissionService;

    public PermissionController(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Permission permission = permissionService.findById(id);
            return ResponseEntity.ok(permission);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/permission/{permission}")
    public ResponseEntity<?> getByPermission(@PathVariable String permission) {
        try {
            Permission found = permissionService.findByPermission(permission);
            return ResponseEntity.ok(found);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Permission permission) {
        try {
            Permission created = permissionService.createPermission(permission);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Permission permission) {
        try {
            Permission updated = permissionService.updatePermission(permission);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            permissionService.deletePermission(id);
            return ResponseEntity.ok("Permission deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

