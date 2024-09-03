package com.example.test.keycloak;

import java.util.UUID;

public class Patient {

    private UUID id;
    private String name;
    private String gender;
    private String birthDate;

    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}

    public String getBirthDate() {return birthDate;}
    public void setBirthDate(String birthDate) {this.birthDate = birthDate;}

}
