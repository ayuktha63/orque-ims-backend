package com.orque.ims.roleaccess.controller;

import com.orque.ims.roleaccess.entity.RoleAccess;
import com.orque.ims.roleaccess.service.RoleAccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoleAccessController {

    private final RoleAccessService service;

    public RoleAccessController(RoleAccessService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RoleAccess>> getAllRoles() {
        return ResponseEntity.ok(service.getAllRoles());
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<RoleAccess> getRoleByName(@PathVariable("roleName") String roleName) {
        return service.getRoleByName(roleName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleAccess> createOrUpdateRole(@RequestBody RoleAccess roleAccess) {
        return ResponseEntity.ok(service.saveOrUpdateRole(roleAccess));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id) {
        service.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
