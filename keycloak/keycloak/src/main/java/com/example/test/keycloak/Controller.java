package com.example.test.keycloak;

import com.example.test.keycloak.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/test")
public class Controller {

    private final PatientService patientService;

    @Autowired
    public Controller(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/patient")
    @PreAuthorize("hasRole('Practitioner')")
    public ResponseEntity<?> create(@RequestBody Patient patient) {
        return new ResponseEntity<>(patientService.create(patient));
    }

    @GetMapping("/patient")
    @PreAuthorize("hasAnyRole('Patient', 'Practitioner')")
    public ResponseEntity<List<Patient>> read() {
        final List<Patient> patients = patientService.readAll();

        return patients != null &&  !patients.isEmpty()
                ? new ResponseEntity<>(patients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/patient/{id}")
    @PreAuthorize("hasAnyRole('Patient', 'Practitioner')")
    public ResponseEntity<Patient> read(@PathVariable(name = "id") UUID id) {
        final Patient patient = patientService.read(id);

        return patient != null
                ? new ResponseEntity<>(patient, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/patient/{id}")
    @PreAuthorize("hasRole('Practitioner')")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody Patient patient) {
        final boolean updated = patientService.update(patient, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/patient/{id}")
    @PreAuthorize("hasRole('Practitioner')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final boolean deleted = patientService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
