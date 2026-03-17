package com.orque.ims.settings.repository;

import com.orque.ims.settings.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {

    Optional<Settings> findByEmployeeId(String employeeId);
}
