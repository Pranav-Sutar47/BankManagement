//package org.example.finalbank;
//
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class ClientsController implements Initializable {
//
//    @FXML
//    private TableColumn<?, ?> accountOpen;
//
//    @FXML
//    private TableColumn<?, ?> accountType;
//
//    @FXML
//    private TableColumn<?, ?> acno;
//
//    @FXML
//    private TableColumn<?, ?> address;
//
//    @FXML
//    private TableColumn<?, ?> balance;
//
//    @FXML
//    private TableColumn<?, ?> dateOfBirth;
//
//    @FXML
//    private TableColumn<?, ?> gender;
//
//    @FXML
//    private TableColumn<?, ?> mobileNo;
//
//    @FXML
//    private TableColumn<?, ?> nm;
//
//    @FXML
//    private TableColumn<?, ?> occupation;
//
//    @FXML
//    private TableView<?> table;
//
//    @FXML
//    private TableColumn<?, ?> uname;
//
//    DbConnection connection;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try{
//            connection = new DbConnection();
//            connection.createConnection();
//
//            connection.ps = connection.con.prepareStatement("SELECT * FROM `clientinfo`");
//            connection.rs = connection.ps.executeQuery();
//
//            while (connection.rs.next()){
//                String ac = connection.rs.getString("accountNumber");
//                String nm = connection.rs.getString("name");
//                String ads = connection.rs.getString("address");
//                String mn = connection.rs.getString("mobileNumber");
//                String dob = connection.rs.getString("dateOfBirth");
//                String ocp = connection.rs.getString("occupation");
//                String gen = connection.rs.getString("gender");
//                String acOpen = connection.rs.getString("accountOpeningDate");
//                String uname = connection.rs.getString("uname");
//                String adno = connection.rs.getString("adharNo");
//                String acType = connection.rs.getString("accountType");
//                Float bal = connection.rs.getFloat("balance");
//                table.getItems().add(new Data(ac,nm,ads,mn,dob,ocp,gen,acOpen,uname,adno,acType,bal));
//
//            }
//
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//}
