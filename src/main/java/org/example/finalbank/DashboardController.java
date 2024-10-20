package org.example.finalbank;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileReader;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.example.finalbank.DbConnection;

public class DashboardController implements Initializable {

    @FXML
    private Label accountNoLabel;

    @FXML
    private Label accountTypeLabel;

    @FXML
    private TextField amount;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private TextArea message;

    @FXML
    private PasswordField mpin;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameLabel1;

    @FXML
    private TextField receiverAC;

    @FXML
    private JFXButton sendAmount;

     //private String uname,psw;
     DbConnection connection;
     int mPin;
     String acNo;
     float balance;
     String acType;
     String nm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        String formattedDate = currentDate.format(formatter);
        System.out.println("Formatted date: " + formattedDate);
        dateLabel.setText("Today is: "+formattedDate);
        try {
            String s="";
            FileReader fileReader = new FileReader("login.txt");
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNextLine())
                s += sc.nextLine();
            int i = s.indexOf('+');
            System.out.println(i);
            String psw = s.substring(i+1,s.length());
            //System.out.println(psw);
            s = s.substring(0,i);
            //System.out.println(s);

            connection = new DbConnection();
            connection.createConnection();

            connection.ps = connection.con.prepareStatement("SELECT * FROM `clientinfo` WHERE `uname`=? AND `Password`=?");
            connection.ps.setString(1,s);
            connection.ps.setString(2,psw);
            connection.rs = connection.ps.executeQuery();

            acNo = "";
            balance=0;
            nm = "";
            acType = "";

            while (connection.rs.next()){
                acNo = connection.rs.getString("accountNumber");
                balance = connection.rs.getFloat("balance");
                nm = connection.rs.getString("name");
                acType = connection.rs.getString("accountType");
                mPin = connection.rs.getInt("mpin");
            }

            nameLabel.setText("Hi "+nm);
            accountTypeLabel.setText(acType);
            accountNoLabel.setText(acNo);
            balanceLabel.setText(balance+" Rs");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public void sendMoney(ActionEvent event) {
        boolean b = valid();
        if(b){
            if(mPin == 0)
                show("Your mpin is not set so you cannot make any payment. Please set mpin to make payment. Mpin is in profile section",0);
            else if(Float.parseFloat(amount.getText())>balance)
                show("Insufficient account balance !!!",0);
            else if( (balance-Float.parseFloat(amount.getText())) > 50 ){
                try{
                    //connection.ps.close();
                    connection.ps = connection.con.prepareStatement("SELECT `name`,`balance` FROM `clientinfo` WHERE `accountNumber`=?;");
                    connection.ps.setString(1,receiverAC.getText());
                    connection.rs= connection.ps.executeQuery();
                    String n="";
                    float m=0;
                    while(connection.rs.next()){
                        n = connection.rs.getString("name");
                        m = connection.rs.getFloat("balance");
                    }
                    System.out.println(n+m);
                    if(n == null || m==0)
                        show("Receiever Account number not found. Please check again",0);
                    else{
                        float a = balance - Float.parseFloat(amount.getText());
                        connection.ps  = connection.con.prepareStatement("UPDATE `clientinfo` SET `balance`=? WHERE `accountNumber`=?;");
                        connection.ps.setFloat(1,a);
                        connection.ps.setString(2,acNo);
                        if(mPin==Integer.parseInt(mpin.getText())){
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Money Transfer?");
                            alert.setContentText("Are you sure to send "+amount.getText()+" Rs to "+n+" ?");
                            ButtonType ok = new ButtonType("Ok");
                            ButtonType cancel = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
                            alert.getButtonTypes().setAll(ok,cancel);
                            alert.showAndWait().ifPresent(response->{
                                if(response==ok){
                                    try {
                                        int i = connection.ps.executeUpdate();
                                        if(i!=0)
                                            show("Money Transferred Successfully!!!",1);
                                        else
                                            show("Error occured!!",0);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });

                            connection.ps = connection.con.prepareStatement("UPDATE `clientinfo` SET `balance`=? WHERE `accountNumber`=?;");
                            m = m+Float.parseFloat(amount.getText());
                            connection.ps.setFloat(1,m);
                            connection.ps.setString(2,receiverAC.getText());
                            int i = connection.ps.executeUpdate();
                            if(i!=0)
                                System.out.println("Successful at receiever end!!!");

                            connection.ps = connection.con.prepareStatement("INSERT INTO `transactions` VALUES(?,?,?,?,1);");
                            connection.ps.setString(1,acNo);
                            connection.ps.setString(2,receiverAC.getText());
                            connection.ps.setFloat(3,Float.parseFloat(amount.getText()));
                            connection.ps.setString(4, message.getText());
                            boolean v =connection.ps.execute();
                            if(v)
                                System.out.println("Success at another table");
                            else
                                System.out.println("Error at another table");
                            clear();
                        }else{
                            show("Incorrect Mpin",0);
                            clear();
                        }
                    }

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }else
                show("Minimum balance should be Rs50.",0);
        }


    }

    boolean valid(){
        if(receiverAC.getText().isEmpty() || amount.getText().isEmpty() || mpin.getText().isEmpty()){
            show("Fill all the field properly",0);
            clear();
            return false;
        }
        else if(receiverAC.getText().length()>12 || amount.getText().length()>12 || mpin.getText().length()!=4){
            show("Invalid Data entered,please provide approprite data",0);
            clear();
            return false;
        }
        else if(acNo.equals(receiverAC.getText())){
            show("Receiever account number and your account number is same",0);
            clear();
            return false;
        }
        else{
            boolean b = containsNumber(receiverAC.getText());
            if(!b){
                show("Account number should contains only digits",0);
                clear();
                return false;
            }
            b = containsNumber(amount.getText());
            if(!b){
                show("Amount should contain only digits",0);
                clear();
                return false;
            }
            b = containsNumber(mpin.getText());
            if(!b){
                show("Mpin should contain only digits",0);
                clear();
                return false;
            }
            return true;
        }
    }

    void clear(){
        receiverAC.setText("");
        amount.setText("");
        mpin.setText("");
        message.setText("");
    }

    public static boolean containsNumber(String str) {
        Pattern pattern = Pattern.compile(".*\\d.*");
        return pattern.matcher(str).matches();
    }

    void show(String msg,int i){
        Alert alert;
        if(i==0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Money Transfer!!!");
        }
        alert.setContentText(msg);
        alert.show();
    }


}
