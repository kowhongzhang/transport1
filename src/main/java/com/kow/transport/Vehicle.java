package com.kow.transport;

import org.bson.types.ObjectId;

import java.util.List;

public class Vehicle {
    private ObjectId id;
    private String licenseNumber;
    private String notes;
    private List<Maintenance> maintenances;

    public ObjectId getId() {
        return id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getNotes() {
        return notes;
    }


    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
