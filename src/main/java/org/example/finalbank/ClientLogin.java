package org.example.finalbank;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientLogin implements Initializable {

    @FXML
    public  BorderPane borderPane;

    public static final EventType<Event> UPDATE_CENTER_PART = new EventType<>("UPDATE_CENTER_PART");

    public static final EventType<Event> UPDATE_PART = new EventType<>("UPDATE_PART");

    public static final EventType<Event> PART = new EventType<>("PART");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(borderPane.getCenter()==null){
            try{
                AnchorPane bp = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                borderPane.setCenter(bp);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        EventBus.getInstance().registerEventHandler(event -> {
            if (event.getEventType() == UPDATE_CENTER_PART) {
                try {
                    loadNewCenterContent();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        EventBus1.getInstance().registerEventHandler(event -> {
            if(event.getEventType()==UPDATE_PART){
                try{
                    loadCenterContent();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });

        EventBus2.getInstance().registerEventHandler(event -> {
            if(event.getEventType()==PART){
                try{
                    loadCenter();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });


    }


    public void loadCenter()throws IOException{
        borderPane.setCenter(null);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        borderPane.setCenter(anchorPane);
    }

    private void loadCenterContent() throws IOException{
        //System.out.println("Hello");
        borderPane.setCenter(null);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        borderPane.setCenter(anchorPane);
    }

    private void loadNewCenterContent() throws IOException {
        //System.out.println("Bye");
        borderPane.setCenter(null);
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("Transactions.fxml"));
        borderPane.setCenter(anchorPane);
    }




//    @Override
//    public void handle(ChangeCenterEvent event) {
//        try {
//            borderPane.setCenter(null);
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(event.getFxmlPath()));
//            AnchorPane anchorPane = loader.load();
//            borderPane.setCenter(anchorPane);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
