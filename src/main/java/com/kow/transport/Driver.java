package com.kow.transport;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Driver {
    private ObjectId id;
    private String name = "";
    private String ic = "";
    private String licenseNumber = "";
    private List<Trip> trips = new ArrayList<Trip>();

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIc() {
        return ic;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setId(ObjectId id) { this.id = id; }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}

