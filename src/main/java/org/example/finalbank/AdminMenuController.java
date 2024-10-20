package org.example.finalbank;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMenuController {

    @FXML
    private JFXButton addClient;

    @FXML
    private JFXButton client;

    @FXML
    private JFXButton deposit;

    @FXML
    private JFXButton logout;


    @FXML
    void logOut(ActionEvent event) throws IOException {
        Stage s = (Stage) logout.getScene().getWindow();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
        s.close();
    }

    public void showClients(ActionEvent event) {
        try{
            EventBus3.getInstance().fireEvent(AdminLogin.UPDATE);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void addClient(ActionEvent event) {
        try{
            EventBus4.getInstance().fireEvent(AdminLogin.SHOW);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
