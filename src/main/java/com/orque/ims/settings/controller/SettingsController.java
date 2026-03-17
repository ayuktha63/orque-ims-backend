package com.orque.ims.settings.controller;

import com.orque.ims.settings.entity.Settings;
import com.orque.ims.settings.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<Settings> getSettings(@PathVariable("employeeId") String employeeId) {
        Optional<Settings> settingsOpt = settingsService.getSettingsByEmployeeId(employeeId);

        return settingsOpt.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    Settings defaultSettings = new Settings();
                    defaultSettings.setEmployeeId(employeeId);
                    defaultSettings.setConfigJson("{}");
                    return ResponseEntity.ok(defaultSettings);
                });
    }

    @PostMapping("/{employeeId}")
    public ResponseEntity<Settings> saveSettings(
            @PathVariable("employeeId") String employeeId,
            @RequestBody Settings payload) {

        Settings saved = settingsService.saveOrUpdateSettings(employeeId, payload.getConfigJson());
        return ResponseEntity.ok(saved);
    }
}
