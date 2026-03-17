package com.orque.ims.settings.service;

import com.orque.ims.settings.entity.Settings;
import com.orque.ims.settings.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    public Optional<Settings> getSettingsByEmployeeId(String employeeId) {
        return settingsRepository.findByEmployeeId(employeeId);
    }

    public Settings saveOrUpdateSettings(String employeeId, String configJson) {
        Optional<Settings> existingOpt = settingsRepository.findByEmployeeId(employeeId);
        Settings settings;
        
        if (existingOpt.isPresent()) {
            settings = existingOpt.get();
        } else {
            settings = new Settings();
            settings.setEmployeeId(employeeId);
        }
        
        settings.setConfigJson(configJson);
        return settingsRepository.save(settings);
    }
}
