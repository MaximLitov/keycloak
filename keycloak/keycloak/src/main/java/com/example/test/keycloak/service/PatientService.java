package com.example.test.keycloak.service;

import com.example.test.keycloak.Patient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PatientService implements PatientServiceInt{

    private static final Map<UUID, Patient> REPOSITORY_MAP = new HashMap<>();
    File file = new File("DB.json");

    public PatientService(){
        try {
            if (!file.exists()) {
                file.createNewFile();
                new ObjectMapper().writeValue(new File("DB.json"), REPOSITORY_MAP.values());
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            List<Patient> patients = mapper.readValue(new File("DB.json"), new TypeReference<>(){});
            for (Patient patient : patients) {
                REPOSITORY_MAP.put(patient.getId(), patient);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateJSON(){
        try {
            new ObjectMapper().writeValue(new File("DB.json"), REPOSITORY_MAP.values());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isDate(String date){
        return date.matches("(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-((18|19|20|21)\\d\\d(T([01][0-9]|2[0-3])(:([0-5][0-9])(:([0-5][0-9]))?)?)?)");
    }

    @Override
    public HttpStatus create(Patient patient) {
        String date = patient.getBirthDate();
        String format = "01-01-2000T00:00:00";
        if (!isDate(date)) return HttpStatus.NOT_ACCEPTABLE;
        date += format.substring(date.length());
        patient.setBirthDate(date);
        final UUID clientId = java.util.UUID.randomUUID();
        patient.setId(clientId);
        REPOSITORY_MAP.put(clientId, patient);
        updateJSON();
        return HttpStatus.CREATED;
    }

    @Override
    public List<Patient> readAll() {
        return new ArrayList<>(REPOSITORY_MAP.values());
    }

    @Override
    public Patient read(UUID id) {
        return REPOSITORY_MAP.get(id);
    }

    @Override
    public boolean update(Patient patient, UUID id) {
        if (!isDate(patient.getBirthDate())) return false;
        if (REPOSITORY_MAP.containsKey(id)) {
            REPOSITORY_MAP.put(id, patient);
            updateJSON();
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        boolean result = REPOSITORY_MAP.remove(id) != null;
        updateJSON();
        return result;
    }
}
