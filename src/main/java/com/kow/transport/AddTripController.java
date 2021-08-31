package com.kow.transport;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

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

    }

    public void setup(){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        quoteField.setTextFormatter(textFormatter);
        gasField.setTextFormatter(textFormatter);
        wageField.setTextFormatter(textFormatter);
    }
}
