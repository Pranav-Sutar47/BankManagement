package org.example.finalbank;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminLogin implements Initializable {

    @FXML
    BorderPane borderPane;
    public static final EventType<Event> UPDATE = new EventType<>("UPDATE");
    public static final EventType<Event> SHOW = new EventType<>("SHOW");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("clientAdd.fxml"));
            borderPane.setCenter(anchorPane);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        // show all clients
        EventBus3.getInstance().registerEventHandler(event -> {
            if (event.getEventType() == UPDATE) {
                try {
                    loadNewCenterContent();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        });
        //show add client
        EventBus4.getInstance().registerEventHandler(event -> {
            if (event.getEventType() == SHOW) {
                try {
                    loadNewContent();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void loadNewContent() throws IOException{
        borderPane.setCenter(null);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("ClientAdd.fxml"));
        borderPane.setCenter(anchorPane);
    }


    private void loadNewCenterContent() throws IOException {
        borderPane.setCenter(null);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("Clients1.fxml"));
        borderPane.setCenter(anchorPane);
    };

}
