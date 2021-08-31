package com.kow.transport;

import org.bson.types.ObjectId;

import java.util.Date;

public class Trip {
    private ObjectId id = new ObjectId();
    private Date date = new Date();
    private String company = "";
    private String origin = "";
    private String destination = "";
    private int quote = 0;
    private int wage = 0;
    private int gas = 0;

    public ObjectId getId() { return id; }

    public Date getDate(){
        return date;
    }

    public String getCompany() {
        return company;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getQuote() {
        return quote;
    }

    public int getWage() {
        return wage;
    }

    public int getGas() {
        return gas;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setQuote(int quote) {
        this.quote = quote;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }
}
