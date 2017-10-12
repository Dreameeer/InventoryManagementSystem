package main;

import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class mysqlconnection{


    public static Connection Connector(){
        final String USER = "root";
        final String PASS = "forgot1";
        final String DB_URL = "jdbc:mysql://localhost:3306/inventory_management_system";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL,USER, PASS);
            return connection;
        } catch (Exception e){
            System.out.println(e);
            return null;
            //TODO: handle exception
        }
    }
}