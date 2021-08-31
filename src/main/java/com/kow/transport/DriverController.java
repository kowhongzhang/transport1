package com.kow.transport;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.Date;

public class DriverController {
    private MongoDatabase db;
    private MongoCollection drivers;

    //data
    private ArrayList<Driver> queryResult = new ArrayList<>();
    private int viewIndex;
    private String lastQuery;
    private ObservableList<Trip> trips = FXCollections.observableList(new ArrayList<>());

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

    @FXML
    private TableView table;
    @FXML
    private TableColumn dateCol;
    @FXML
    private TableColumn companyCol;
    @FXML
    private TableColumn originCol;
    @FXML
    private TableColumn destinationCol;
    @FXML
    private Button addTripButton;

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
            trips = FXCollections.observableList(queryResult.get(viewIndex).getTrips());
            updateCount();
        }
    }

    public void queryLastResult(){
        queryIC(lastQuery);
        if (!queryResult.isEmpty()){
            displayDriver(queryResult.get(viewIndex));
            trips = FXCollections.observableList(queryResult.get(viewIndex).getTrips());
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
    private void displayNext(){
        if (viewIndex+1<queryResult.size()){
            viewIndex += 1;
            updateCount();
            Driver next = queryResult.get(viewIndex);
            trips = FXCollections.observableList(queryResult.get(viewIndex).getTrips());
            displayDriver(next);
        }
    }

    @FXML
    private void displayPrevious(){
        if (viewIndex-1>=0 && viewIndex-1<queryResult.size()){
            viewIndex -= 1;
            updateCount();
            Driver prev = queryResult.get(viewIndex);
            trips = FXCollections.observableList(queryResult.get(viewIndex).getTrips());
            displayDriver(prev);
        }
    }

    private void updateCount(){
        currentIndexField.setText(Integer.toString(viewIndex+1));
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
        driver.setName("name");
        driver.setIc("ic");
        driver.setLicenseNumber("license number");
        displayDriver(driver);
        drivers.insertOne(driver);
        queryResult = (ArrayList<Driver>) drivers.find(Filters.eq("_id",driver.getId())).into(new ArrayList());
        viewIndex = 0;
        updateCount();
    }

    @FXML
    private void deleteDriver(){
        if (queryResult.size()!=0) {
            drivers.deleteOne(Filters.eq("_id", queryResult.get(viewIndex).getId()));
            if (queryResult.size()==1) {
                clearDisplay();
                queryResult = new ArrayList<>();
            } else if (viewIndex==queryResult.size()-1){
                viewIndex-=1;
                queryLastResult();
            } else {
                queryLastResult();
            }
        }
    }

    public void setupColumnProperties(){
        dateCol.setCellFactory(new PropertyValueFactory<Trip, Date>("date"));
        companyCol.setCellFactory(new PropertyValueFactory<Trip, String>("company"));
        originCol.setCellFactory(new PropertyValueFactory<Trip,String>("origin"));
        destinationCol.setCellFactory(new PropertyValueFactory<Trip,String>("destination"));
        table.setItems(trips);
    }

    @FXML
    private void addTrip() throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddTrip.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            Scene newScene = new Scene(root);
            newStage.setScene(newScene);
            AddTripController controller = loader.getController();
//            controller.setup();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();
        } catch (Exception e) {
            System.out.println("failed to load window");
        }
    }




}
