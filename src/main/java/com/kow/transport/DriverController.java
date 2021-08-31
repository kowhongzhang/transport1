package com.kow.transport;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;

public class DriverController {
    private MongoDatabase db;
    private MongoCollection drivers;
    private ArrayList<Driver> queryResult = new ArrayList<>();
    private int viewIndex;
    private String lastQuery;
    @FXML
    private TextField nameField;
    @FXML
    private TextField icField;
    @FXML
    private TextField licenseField;

    @FXML
    private TextField icQueryField;
    @FXML
    private TextField currentIndexField;
    @FXML
    private TextField lastIndexField;

    public MongoDatabase getDb() {
        return db;
    }

    public void setDb(MongoDatabase db) {
        this.db = db;
        getDrivers();
    }

    private void getDrivers () {
        drivers = db.getCollection("drivers",Driver.class);
    }

    private void queryIC (String ic) {
        lastQuery = ic;
        if (ic == null) {
            return;
        } else if (ic .equals("")) {
            queryResult = (ArrayList<Driver>) drivers.find().into(new ArrayList<Driver>());
        } else {
            queryResult = (ArrayList<Driver>) drivers.find(Filters.eq("ic", ic)).into(new ArrayList<Driver>());
        }
    }

    @FXML
    public void queryAndDisplay (){
        queryIC(icQueryField.getText());
        if (!queryResult.isEmpty()){
            displayDriver(queryResult.get(0));
            viewIndex = 0;
            System.out.println(queryResult.size());
            updateCount();
        }
    }

    public void queryLastResult(){
        queryIC(lastQuery);
        if (!queryResult.isEmpty()){
            displayDriver(queryResult.get(viewIndex));
            updateCount();
        }
    }

    private void displayDriver(Driver driver){
        nameField.setText(driver.getName());
        icField.setText(driver.getIc());
        licenseField.setText(driver.getLicenseNumber());
    }

    private void clearDisplay() {
        nameField.setText("");
        icField.setText("");
        licenseField.setText("");
        currentIndexField.setText("");
        lastIndexField.setText("");
    }

    @FXML
    private void AddTestDrivers (){
        Driver d1 = new Driver();
        Driver d2 = new Driver();
        Driver d3 = new Driver();
        d1.setName("abu");
        d1.setIc("0");
        d1.setLicenseNumber("123");
        d2.setName("ali");
        d2.setIc("1");
        d2.setLicenseNumber("1234");
        d3.setName("akow");
        d3.setIc("2");
        d3.setLicenseNumber("1235");
        drivers.insertOne(d1);
        drivers.insertOne(d2);
        drivers.insertOne(d3);

    }

    @FXML
    private void displayNext(){
        if (viewIndex+1<queryResult.size()){
            viewIndex += 1;
            updateCount();
            Driver next = queryResult.get(viewIndex);
            displayDriver(next);
        }
    }

    @FXML
    private void displayPrevious(){
        if (viewIndex-1>=0 && viewIndex-1<queryResult.size()){
            viewIndex -= 1;
            updateCount();
            Driver prev = queryResult.get(viewIndex);
            displayDriver(prev);
        }
    }

    private void updateCount(){
        currentIndexField.setText(Integer.toString(viewIndex+1));
        System.out.println(queryResult.size());
        lastIndexField.setText(Integer.toString(queryResult.size()));
    }

    @FXML
    private void saveDriver(){
        if (queryResult.size()!=0) {
            Bson filter = Filters.eq("_id", queryResult.get(viewIndex).getId());
            Bson setName = Updates.set("name", nameField.getText());
            Bson setIc = Updates.set("ic", icField.getText());
            Bson setLicense = Updates.set("licenseNumber", licenseField.getText());
            drivers.updateOne(filter, Updates.combine(setName, setIc, setLicense));
            queryLastResult();
        }
    }

    @FXML
    private void newDriver(){
        clearDisplay();
        Driver driver = new Driver();
        displayDriver(driver);
        System.out.println(drivers.insertOne(driver));
        queryResult = (ArrayList<Driver>) drivers.find(Filters.eq("_id",driver.getId())).into(new ArrayList());
        viewIndex = 0;
        updateCount();
    }

    @FXML
    private void deleteDriver(){

    }




}
