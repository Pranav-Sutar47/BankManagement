//package org.example.finalbank;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXRadioButton;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.TextField;
//import javafx.scene.control.ToggleGroup;
//
//import java.net.URL;
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.ResourceBundle;
//
//public class ClientAddController implements Initializable {
//
//    @FXML
//    private TextField address;
//
//    @FXML
//    private DatePicker date;
//
//    @FXML
//    private DatePicker dateOfBirth;
//
//    @FXML
//    private JFXRadioButton femaleRadio;
//
//    @FXML
//    private JFXRadioButton maleRadio;
//
//    @FXML
//    private TextField mobileNo;
//
//    @FXML
//    private TextField name;
//
//    @FXML
//    private TextField occupation;
//
//    @FXML
//    private JFXButton submit;
//
//
//
//    public void showData(ActionEvent event){
//        String nm = name.getText();
//        name.setText("");
//        String mn = mobileNo.getText();
//        mobileNo.setText("");
//        String op = occupation.getText();
//        occupation.setText("");
//        //LocalDate d = date.getValue();
//        //String dt = d.toString();
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        toggleGroup = new ToggleGroup();
//        maleRadio.setToggleGroup(toggleGroup);
//        femaleRadio.setToggleGroup(toggleGroup);
//    }
//}


package org.example.finalbank;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.example.finalbank.DbConnection;
import java.util.regex.Pattern;

public class ClientAddController implements Initializable {

    @FXML
    private DatePicker accountDateOpen;

    @FXML
    private TextField address;

    @FXML
    private TextField adharNo;

    @FXML
    private JFXRadioButton currentAccount;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private JFXRadioButton femaleRadio;

    @FXML
    private JFXRadioButton maleRadio;

    @FXML
    private TextField mobileNo;

    @FXML
    private TextField name;

    @FXML
    private TextField occupation;

    @FXML
    private JFXRadioButton savingsAccount;

    @FXML
    private JFXButton submit;

    @FXML
    private TextField uname;

    DbConnection connection;
    ToggleGroup toggleGroup1,toggleGroup2;

    @FXML
    void showData(ActionEvent event) {
        boolean b = check();
        validData();
        if(b==false){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Missing data or Error in data, Check all fields, Mobile number and adhar number must be in digit and also length must be valid");
            alert.show();
        }
       else{
           try{
               connection.ps = connection.con.prepareStatement("INSERT INTO `clientinfo`VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?);");
               connection.ps.setString(1,name.getText());
               connection.ps.setString(2,address.getText());
               connection.ps.setString(3,mobileNo.getText());
               LocalDate localDate = dateOfBirth.getValue();
               Date date = Date.valueOf(localDate);
               connection.ps.setDate(4,date);
               connection.ps.setString(5,occupation.getText());
               if(maleRadio.isSelected())
                   connection.ps.setString(6,maleRadio.getText());
               else
                   connection.ps.setString(6,femaleRadio.getText());
               localDate = accountDateOpen.getValue();
               date = Date.valueOf(localDate);
               connection.ps.setDate(7,date);
               connection.ps.setString(8,uname.getText());
               connection.ps.setString(9,adharNo.getText());
               if(savingsAccount.isSelected())
                   connection.ps.setString(10,savingsAccount.getText());
               else
                   connection.ps.setString(10,currentAccount.getText());
               connection.ps.setString(11,mobileNo.getText());
               connection.ps.setFloat(12,50);
               connection.ps.setInt(13,0);
               int i = 0;
               i = connection.ps.executeUpdate();
               connection.ps.close();

               if(i!=0){
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Bank Account Created!!");
                   connection.ps = connection.con.prepareStatement("SELECT `accountNumber` FROM `clientinfo` WHERE mobileNumber = ?;");
                   connection.ps.setString(1,mobileNo.getText());
                   connection.rs = connection.ps.executeQuery();
                   String s ="";
                   while (connection.rs.next())
                       s = connection.rs.getString("accountNumber");
                   alert.setContentText("Your bank account created Successfully!!!\nYour account number is: "+s+"\nUse Mobile Number as password to login and change it in profile section\nYour Account Balance is:50 Rs");
                   alert.show();
                   clear();
                   connection.closeConnection();
               }else
                   showDialog("Account Not created !!!");

           }catch (Exception e) {
               System.out.println(e.getMessage());
           }
        }
    }


    void clear(){
        uname.setText("");
        occupation.setText("");
        address.setText("");
        name.setText("");
        adharNo.setText("");
        mobileNo.setText("");
        savingsAccount.setSelected(false);
        currentAccount.setSelected(false);
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
        dateOfBirth.setValue(null);
        accountDateOpen.setValue(null);
    }

    void showDialog(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.show();
    }

    void validData(){
        if(mobileNo.getText().length()!=10)
            showDialog("Mobile Number must be of 10 digits");
        if(adharNo.getText().length()!=12)
            showDialog("Adhar Number must be of 12 digits");
        boolean b = containsNumber(name.getText());
        if(b == true)//string contains number
            showDialog("Name should not contain any Digits");
        b = containsNumber(adharNo.getText());
        if(b==false)//string contains char
            showDialog("Adhar number should not contain any character");
        b = containsNumber(mobileNo.getText());
        if(b==false)
            showDialog("Mobile Number should not conatain any character");
        if(uname.getText().length()>=10 && uname.getText().length()<=2)
            showDialog("Username should in character greater than 2 and less than 10");
        b = containsNumber(uname.getText());
        if(b==true)
            showDialog("Username should not contain any character");
    }

    public static boolean containsNumber(String str) {
        Pattern pattern = Pattern.compile(".*\\d.*");
        return pattern.matcher(str).matches();
    }

    boolean check(){
        if(name.getText().isEmpty())
            return false;
        if(mobileNo.getText().isEmpty())
            return false;
        if(address.getText().isEmpty())
            return false;
        if(occupation.getText().isEmpty())
            return false;
        if(uname.getText().isEmpty())
            return false;
        if(maleRadio.isSelected() == false && femaleRadio.isSelected()==false)
            return false;
        if(savingsAccount.isSelected() == false && currentAccount.isSelected() == false)
            return false;
        if(adharNo.getText().isEmpty())
            return false;
        LocalDate localDate = dateOfBirth.getValue();
        if(localDate==null)
            return false;
        localDate = accountDateOpen.getValue();
        if(localDate==null)
            return false;

        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroup1 = new ToggleGroup();
        maleRadio.setToggleGroup(toggleGroup1);
        femaleRadio.setToggleGroup(toggleGroup1);
        toggleGroup2 = new ToggleGroup();
        savingsAccount.setToggleGroup(toggleGroup2);
        currentAccount.setToggleGroup(toggleGroup2);
        connection = new DbConnection();
        connection.createConnection();
    }

}
