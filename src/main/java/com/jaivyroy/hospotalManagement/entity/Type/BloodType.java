package com.jaivyroy.hospotalManagement.entity.Type;

public enum BloodType {
    O_POSITIVE("O+"),
    O_NEGATIVE("O-"),
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-");

    private final String bloodGroup;

    // Constructor to initialize the blood group value
    BloodType(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    // Getter method to retrieve the blood group value
    public String getBloodGroup() {
        return bloodGroup;
    }
}