package org.example.finalbank;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import com.jfoenix.controls.JFXButton;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.example.finalbank.DbConnection;

public class LoginController implements Initializable {

    @FXML
    private VBox leftSide;

    @FXML
    private JFXButton login;

    @FXML
    private ChoiceBox<String> loginType;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    DbConnection connection;

    @FXML
    void loginMethod(ActionEvent event) throws IOException, SQLException {

        if(username.getText().isEmpty() || password.getText().isEmpty() || loginType.getSelectionModel()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill all the fields");
            alert.show();
        }else{
            if(loginType.getValue().equals("Admin")){
                if(username.getText().equals("Admin") && password.getText().equals("Admin")){
                    Stage s = (Stage) login.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Admin Login");
                    //stage.getIcons().add(new Image("Images/bank.png"));
                    s.close();
                    stage.show();
                }
                else{
                    show("Invalid credential !!!",0);
                    clear();
                }

            }
            else{
                try{
                    connection.ps = connection.con.prepareStatement("SELECT `uname`,`Password` FROM `clientinfo` WHERE `uname`=? AND `Password`=?;");
                    connection.ps.setString(1,username.getText());
                    connection.ps.setString(2,password.getText());
                    connection.rs = connection.ps.executeQuery();
                    if(!connection.rs.next()){
                        show("Invalid Credientials\nInvalid username or password",0);
                        clear();

                    }else{
                        FileWriter fileWriter = new FileWriter("login.txt");
                        fileWriter.write(username.getText()+"+"+password.getText());
                        fileWriter.close();
                        Stage s = (Stage) login.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientLogin.fxml"));
                        Parent root =loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
//                        DashboardController dashboardController = loader.getController();
//                        dashboardController.setValue(username.getText(),password.getText());
                        stage.setScene(scene);
                        stage.setTitle("Client Login");
                        //stage.getIcons().add(new Image("Images/bank.png"));
                        //dashboardController = new DashboardController(username.getText(),password.getText());
                        s.close();
                        stage.show();
                    }
                }catch (Exception e){
                    connection.closeConnection();
                    System.out.println(e.getMessage());
                }

            }
        }



    }


    void clear(){
        password.setText("");
        username.setText("");
        loginType.setValue("");
    }

    void show(String msg,int b){
        Alert alert = null;
        if(b==0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(msg);
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(msg);
        }
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> observableList = FXCollections.observableArrayList("Admin","Client");
        loginType.setItems(observableList);
        try {
            connection = new DbConnection();
            connection.createConnection();
        }catch (Exception e){
            System.out.println(e.getMessage());;
        }
    }
}

