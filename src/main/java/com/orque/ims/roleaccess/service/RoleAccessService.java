package com.orque.ims.roleaccess.service;

import com.orque.ims.roleaccess.entity.RoleAccess;
import com.orque.ims.roleaccess.repository.RoleAccessRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleAccessService {

    private final RoleAccessRepository repository;

    public RoleAccessService(RoleAccessRepository repository) {
        this.repository = repository;
    }

    public List<RoleAccess> getAllRoles() {
        return repository.findAll();
    }

    public Optional<RoleAccess> getRoleByName(String roleName) {
        return repository.findByRoleName(roleName);
    }

    public RoleAccess saveOrUpdateRole(RoleAccess roleAccess) {
        Optional<RoleAccess> existing = repository.findByRoleName(roleAccess.getRoleName());
        if (existing.isPresent()) {
            RoleAccess toUpdate = existing.get();
            toUpdate.setAccessConfigJson(roleAccess.getAccessConfigJson());
            return repository.save(toUpdate);
        }
        return repository.save(roleAccess);
    }

    public void deleteRole(Long id) {
        repository.deleteById(id);
    }
}
