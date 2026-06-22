package com.agendador.back.service;

import com.agendador.back.entity.PatientEntity;
import com.agendador.back.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Value("${admin.pass}")
    private String adminPass;

    public boolean authenticate(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return password.equals(adminPass);
    }

    public List<PatientEntity> listAllPatients() {
        return patientRepository.findAllByOrderByIdDesc();
    }

    public Optional<PatientEntity> createPatient(PatientEntity patient) {
        if (patient.getNome() == null || patient.getEmail() == null ||
                patient.getTel() == null || patient.getModal() == null ||
                patient.getData() == null || patient.getHora() == null) {
            return Optional.empty(); // Retorna vazio indicando falha na validação
        }

        if (patient.getStatus() == null) {
            patient.setStatus("pendente");
        }

        return Optional.of(patientRepository.save(patient));
    }

    public Optional<PatientEntity> updatePatientStatus(Long id, Map<String, Object> updates) {
        return patientRepository.findById(id).map(patient -> {

            if (updates.containsKey("status") && updates.get("status") != null) {
                patient.setStatus((String) updates.get("status"));
            }

            if (updates.containsKey("calId")) {
                patient.setCalId((String) updates.get("calId"));
            }

            return patientRepository.save(patient);
        });
    }


    public boolean deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            return false;
        }
        patientRepository.deleteById(id);
        return true;
    }
}