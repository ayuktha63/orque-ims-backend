package com.orque.ims.credential.CredentialService;

import com.orque.ims.credential.CredentialDTO.CredentialDto;
import com.orque.ims.credential.Entity.Credential;
import com.orque.ims.credential.CredentialRepository.CredentialRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialRepository repository;

    public CredentialService(CredentialRepository repository) {
        this.repository = repository;
    }

    public List<CredentialDto> getFullCredentialList() {
        return repository.findAllEmployeesWithCredentials();
    }

    public void upsertCredential(Credential credential) {
        // Logic to enable UPDATE: If employeeId exists, attach existing primary key ID
        repository.findByEmployeeId(credential.getEmployeeId()).ifPresent(existing -> {
            credential.setId(existing.getId());
        });
        repository.save(credential);
    }
}