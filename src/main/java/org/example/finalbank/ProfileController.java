package org.example.finalbank;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.FileReader;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProfileController implements Initializable {

    @FXML
    private TextField accountType;

    @FXML
    private TextField adNo;

    @FXML
    private TextField address;

    @FXML
    private DatePicker bdate;

    @FXML
    private JFXButton changePsw;

    @FXML
    private TextField dateOfBirth;

    @FXML
    private TextField gender;

    @FXML
    private TextField mobileNo;

    @FXML
    private TextField name;

    @FXML
    private PasswordField newPsw;

    @FXML
    private TextField occupation;

    @FXML
    private PasswordField oldPsw;

    @FXML
    private TextField uname;

    @FXML
    private PasswordField oldMpin;

    @FXML
    private PasswordField newMpin;

    @FXML
    private PasswordField psw;

    @FXML
    private JFXButton createMpin;
    DbConnection connection;
    String p,ad;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = new DbConnection();
        try{
            connection.createConnection();
            String s="";
            FileReader fileReader = new FileReader("login.txt");
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNextLine())
                s += sc.nextLine();
            int i = s.indexOf('+');
            String psw = s.substring(i+1,s.length());
            s = s.substring(0,i);

            connection.ps = connection.con.prepareStatement("SELECT * FROM `clientinfo` WHERE `uname`=? AND `Password`=?");
            connection.ps.setString(1,s);
            connection.ps.setString(2,psw);
            connection.rs = connection.ps.executeQuery();

            while (connection.rs.next()){
                name.setText(connection.rs.getString("name"));
                address.setText(connection.rs.getString("address"));
                mobileNo.setText(connection.rs.getString("mobileNumber"));
                dateOfBirth.setText(connection.rs.getDate("dateOfBirth").toString());
                occupation.setText(connection.rs.getString("occupation"));
                gender.setText(connection.rs.getString("gender"));
                uname.setText(connection.rs.getString("uname"));
                accountType.setText(connection.rs.getString("accountType"));
                p = connection.rs.getString("Password");
                ad = connection.rs.getString("adharNo");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void changePassword(ActionEvent event) {
        boolean b = valid();
        if(b){
            try{

                if(oldPsw.getText().equals(p)){
                    connection.ps = connection.con.prepareStatement("UPDATE `clientinfo` SET `Password`=? WHERE adharNo = ?  AND dateOfBirth = ?;");
                    connection.ps.setString(1,newPsw.getText());
                    connection.ps.setString(2,adNo.getText());
                    Date d = Date.valueOf(bdate.getValue());
                    connection.ps.setDate(3, d);
                    int i = connection.ps.executeUpdate();
                    if(i==0)
                        show("Invalid details entered.Check your New Password,Adhar Number and Date of Birth",0);
                    else
                        show("Password Changed Successfully !!",1);
                    clear();
                }else
                    show("Incorrect Old Password",0);

            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }else
            clear();
    }

    void clear(){
        oldPsw.setText("");
        newPsw.setText("");
        adNo.setText("");
        bdate.setValue(null);
    }

    boolean valid(){
        if(oldPsw.getText().isEmpty() || newPsw.getText().isEmpty() || adNo.getText().isEmpty() || bdate.getValue()==null){
            show("Fill all the field properly",0);
            return false;
        }
        else if(!newPsw.getText().isEmpty()){
            if(newPsw.getText().length()>12 || newPsw.getText().length()<=2){
                show("Invalid Password,Password length must be greater than 2 and less than 13 character",0);
                return false;
            }
            else
                return true;
        }
        else
            return true;
    }

    void show(String msg ,int i){
        Alert alert;
        if(i==0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Changed");
        }
        alert.setContentText(msg);
        alert.show();
    }

    public void createMpin(ActionEvent event) {
        boolean b = valid1();
        if(b){
            try{
                connection.ps = connection.con.prepareStatement("UPDATE `clientinfo` SET mpin=? WHERE `adharNo`=? AND `Password` = ?; ");
                connection.ps.setInt(1,Integer.parseInt(newMpin.getText()));
                connection.ps.setString(2,ad);
                connection.ps.setString(3,psw.getText());
                int i = connection.ps.executeUpdate();

                if(i==0){
                    show("Error Occured",0);
                }else
                    show("Your Mpin Created Successfully !!!",1);
                clear1();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    boolean valid1(){
        boolean b = false;
        containsNumber(newMpin.getText());
        if(oldMpin.getText().isEmpty() || newMpin.getText().isEmpty() || psw.getText().isEmpty()){
            show("Fill all the fields",0);
            clear1();
            return false;
        }else if(!oldMpin.getText().equals(newMpin.getText())){
            show("Mpin and Reentered Mpin are not same",0);
            clear1();
            return false;
        }else if(oldMpin.getText().length()!=4 && newMpin.getText().length()!=4){
            clear1();
            show("Mpin must be of length 4 and contains only digits",0);
            return false;
        }else if(!b){
            clear1();
            show("Mpin contains characters",0);
            return false;
        }else
            return true;
    }

    void clear1(){
        oldMpin.setText("");
        newMpin.setText("");
        psw.setText("");
    }

    public static boolean containsNumber(String str) {
        Pattern pattern = Pattern.compile(".*\\d.*");
        return pattern.matcher(str).matches();
    }
}
