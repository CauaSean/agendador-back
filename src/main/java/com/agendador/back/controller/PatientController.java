package com.agendador.back.controller;

import com.agendador.back.entity.PatientEntity;
import com.agendador.back.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String password = body.get("password");

        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Senha não informada."));
        }

        if (patientService.authenticate(password)) {
            return ResponseEntity.ok(Map.of("ok", true));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("ok", false, "error", "Senha incorreta."));
    }

    @GetMapping("/patients")
    public List<PatientEntity> listAll() {
        return patientService.listAllPatients();
    }

    @PostMapping("/patients")
    public ResponseEntity<?> create(@RequestBody PatientEntity patient) {
        patient.setId(null);

        java.util.Optional<PatientEntity> saved = patientService.createPatient(patient);

        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(saved.get());
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Campos obrigatórios faltando."));
        }
    }

    @PatchMapping("/patients/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        java.util.Optional<PatientEntity> updated = patientService.updatePatientStatus(id, body);

        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Paciente não encontrado."));
        }
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (patientService.deletePatient(id)) {
            return ResponseEntity.ok(Map.of("ok", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Paciente não encontrado."));
    }
}