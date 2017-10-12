package MainMethodsAndDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectToDatabase {
    public Connection Connect(){
        try{
            final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            final String DB_URL = "jdbc:mysql://localhost:3306/inventory_management_system";

            //  Duplicatecodes.Database credentials
            final String USER = "root";
            final String PASS = "forgot1";
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Database connection requested");
            return conn;
         } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }}
