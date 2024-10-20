package org.example.finalbank;

import java.sql.*;

public class DbConnection {
    public Connection con;

    public ResultSet rs;
    public PreparedStatement ps;

    void createConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankapp","root","");
            ps= con.prepareStatement("SELECT * FROM `clientinfo`;");
            rs = ps.executeQuery();
            byte b=0;
            while (rs.next())
                b++;
            System.out.println(b);
            System.out.println("Connection Successful!!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    void closeConnection() throws SQLException {
        con.close();
        ps.close();
        rs.close();
    }

}
