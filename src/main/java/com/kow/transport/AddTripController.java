package com.kow.transport;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.types.ObjectId;

public class AddTripController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField companyField;
    @FXML
    private TextField quoteField;
    @FXML
    private TextField originField;
    @FXML
    private TextField destinationField;
    @FXML
    private TextField wageField;
    @FXML
    private TextField gasField;
    @FXML
    private Button submitButton;
    private ObjectId driverId;
    private MongoCollection drivers;

    @FXML
    private void submit(){
        Trip trip = new Trip();
        if (datePicker.getValue()!=null){
            trip.setDate(datePicker.getValue());
        }
        if (companyField.getText()!=null){
            trip.setCompany(companyField.getText());
        }
        if (originField.getText()!=null){
            trip.setOrigin(originField.getText());
        }
        if (destinationField.getText()!=null){
            trip.setDestination(destinationField.getText());
        }
        if (quoteField.getText().matches("[0-9]*") && quoteField.getText()!="") {
            trip.setQuote(Integer.parseInt(quoteField.getText()));
        }
        if (wageField.getText().matches("[0-9]*") && wageField.getText()!="") {
            trip.setWage(Integer.parseInt(wageField.getText()));
        }
        if (gasField.getText().matches("[0-9]*") && gasField.getText()!="") {
            trip.setGas(Integer.parseInt(gasField.getText()));
        }

        drivers.updateOne(Filters.eq("_id",driverId), Updates.push("trips",trip));
        Stage currentStage = (Stage) submitButton.getScene().getWindow();
        currentStage.close();
    }

    public void setDriverId(ObjectId driverId) {
        this.driverId = driverId;
    }

    public void setDrivers(MongoCollection drivers) {
        this.drivers = drivers;
    }
}
