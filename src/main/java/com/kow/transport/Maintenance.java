package com.kow.transport;

import java.util.Date;

import org.bson.types.ObjectId;

public class Maintenance {

    private ObjectId id;
    private Date date;
    private String serviceCompany;
    private int cost;
    private String issue;
    private String notes;


    public ObjectId getId(){
        return id;
    }

    public String getNotes() {
        return notes;
    }

    public String getServiceCompany() {
        return serviceCompany;
    }

    public int getCost() {
        return cost;
    }

    public String getIssue() {
        return issue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setServiceCompany(String serviceCompany) {
        this.serviceCompany = serviceCompany;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}
