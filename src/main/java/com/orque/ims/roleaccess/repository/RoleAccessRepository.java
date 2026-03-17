package com.orque.ims.roleaccess.repository;

import com.orque.ims.roleaccess.entity.RoleAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleAccessRepository extends JpaRepository<RoleAccess, Long> {
    Optional<RoleAccess> findByRoleName(String roleName);
}
