package org.example.finalbank;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.finalbank.ClientLogin;
import java.io.IOException;

public class ClientMenu {

    @FXML
    private JFXButton accounts;

    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton profile;

    @FXML
    private JFXButton transaction;


    @FXML
    void logOut(ActionEvent event) throws IOException {
        Stage s = (Stage) logout.getScene().getWindow();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        s.close();
        stage.show();
    }



    @FXML
    void getTransactions(ActionEvent event) throws IOException {
        try{
            //FXMLLoader anchorPane = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            //Node centerContent = anchorPane.load();
            EventBus.getInstance().fireEvent(ClientLogin.UPDATE_CENTER_PART);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
////        if (centerChangeEventHandler != null) {
////            centerChangeEventHandler.handle(new ChangeCenterEvent("Transactions.fxml"));
////        }


    }

    @FXML
    public void getProfile(ActionEvent event) {
        try{
            EventBus1.getInstance().fireEvent(ClientLogin.UPDATE_PART);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void getDashboard(ActionEvent event) {
        try{
            EventBus2.getInstance().fireEvent(ClientLogin.PART);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
