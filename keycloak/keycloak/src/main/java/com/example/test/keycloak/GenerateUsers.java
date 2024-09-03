package com.example.test.keycloak;

import com.example.test.keycloak.Patient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateUsers {

    public static void main(String[] args){
        try {
            File file = new File("DB.json");
            List<Patient> patients = new ArrayList<>();
            if (!file.exists()) {
                file.createNewFile();
                new ObjectMapper().writeValue(new File("DB.json"), patients);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                patients = mapper.readValue(new File("DB.json"), new TypeReference<>(){});
            }

            System.out.println("Запуск генерации 100 пользователей");
            for (int i = 0; i < 100; i++) {
                Patient patient = new Patient();
                patient.setId(java.util.UUID.randomUUID());
                patient.setName("Иванов Иван Иванович " + i);
                patient.setGender("male");
                patient.setBirthDate("01-01-2000T00:00:00");
                patients.add(patient);
                System.out.println("Пользователь " + (i + 1) + " успешно сгенерирован.");
            }
            System.out.println("Генерация завершена.");
            new ObjectMapper().writeValue(file, patients);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
