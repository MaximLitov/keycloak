package com.example.test.keycloak.service;

import com.example.test.keycloak.Patient;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

public interface PatientServiceInt {
    HttpStatus create(Patient patient);
    List<Patient> readAll();
    Patient read(UUID id);
    boolean update (Patient patient, UUID id);
    boolean delete (UUID id);
}
