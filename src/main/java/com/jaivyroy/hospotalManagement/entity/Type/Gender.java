package com.jaivyroy.hospotalManagement.entity.Type;



public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String gender;

    // Constructor to initialize the gender string
    Gender(String gender) {
        this.gender = gender;
    }

    // Getter to retrieve the gender string
    public String getGender() {
        return gender;
    }
}