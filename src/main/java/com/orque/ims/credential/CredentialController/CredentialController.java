package com.orque.ims.credential.CredentialController;

import com.orque.ims.credential.CredentialDTO.CredentialDto;
import com.orque.ims.credential.Entity.Credential;
import com.orque.ims.credential.CredentialService.CredentialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/credentials")
public class CredentialController {

    private final CredentialService service;

    public CredentialController(CredentialService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CredentialDto>> getAll() {
        return ResponseEntity.ok(service.getFullCredentialList());
    }

    @PostMapping
    public ResponseEntity<String> saveOrUpdate(@RequestBody Credential credential) {
        service.upsertCredential(credential);
        return ResponseEntity.ok("Credential updated successfully");
    }
}