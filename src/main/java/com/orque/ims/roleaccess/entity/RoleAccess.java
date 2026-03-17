package com.orque.ims.roleaccess.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_access")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RoleAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roleName;

    @Column(columnDefinition = "TEXT")
    private String accessConfigJson;
}
