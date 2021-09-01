package com.kow.transport;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXMLLoader;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Controller {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    private TextField connectionText;

    @FXML
    private TextField dbText;


    @FXML
    public void connect(ActionEvent event) {

        try {
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder()
                    .automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                    pojoCodecRegistry);
            MongoClient client = MongoClients.create(connectionText.getText());
            MongoDatabase db = client.getDatabase(dbText.getText()).withCodecRegistry(codecRegistry);

            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Driver.fxml"));
            root = loader.load();
            DriverController driverController = loader.getController();

            //setup new controller
            driverController.setDb(db);
            driverController.setupColumnProperties();

            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Transport");
            stage.show();
        } catch (Exception e) {
            System.out.println("Connection failed");
        }
    }
}


