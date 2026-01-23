package com.orque.ims.credential.CredentialRepository;

import com.orque.ims.credential.Entity.Credential;
import com.orque.ims.credential.CredentialDTO.CredentialDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findByUsername(String username);
    Optional<Credential> findByEmployeeId(Long employeeId);

    @Query("SELECT new com.orque.ims.credential.CredentialDTO.CredentialDto(" +
            "e.id, e.name, e.department, c.username, c.password, c.role, " + // ADDED c.password
            "(CASE WHEN c.username IS NOT NULL THEN true ELSE false END)) " +
            "FROM Employee e LEFT JOIN Credential c ON e.id = c.employeeId")
    List<CredentialDto> findAllEmployeesWithCredentials();
}