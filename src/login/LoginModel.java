package login;

import main.mysqlconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;


import java.sql.*;

public class LoginModel {
    static Connection connection;

    public LoginModel() {
        connection = mysqlconnection.Connector();
        if (connection == null) {
            System.out.println("Connection not Successfull");
            System.exit(1);
        }

    }

    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isAuthorized(String use, String pas) throws SQLException {
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        String query = "select * from admin_list where `Admin UserName` = ? and `Admin Password` = ?";
        System.out.println(query);

        try {
            preparedStatement1 = connection.prepareStatement(query);
            System.out.println(use);
            System.out.println(pas);
            preparedStatement1.setString(1, use);
            preparedStatement1.setString(2, pas);
            System.out.println("THISSSSSSSSS");
            System.out.println(use);
            System.out.println(pas);
            System.out.println(query);
            System.out.println("Reached 1");
            System.out.println(preparedStatement1);
            resultSet1 = preparedStatement1.executeQuery();

            System.out.println("Reached 2");
            if (resultSet1.next()) {
                System.out.println("A");
                return true;
            } else {
                System.out.println("A");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Catched");
            return false;
        }
    }

    public static boolean isLogin(String user, String pass) throws SQLException {
        System.out.println("1");
        PreparedStatement preparedStatement = null;
        System.out.println("2");
        ResultSet resultSet = null;
        System.out.println("3");
        String query = "select * from `authorized list` where `User Name`= ? and `User Password` = ?";
        System.out.println(query);


        try {
            System.out.println("4");
            preparedStatement = connection.prepareStatement(query);
            System.out.println("5");
            preparedStatement.setString(1, user);
            System.out.println("6");
            preparedStatement.setString(2, pass);
            System.out.println("THISSSSSSSSSSSSSS");
            System.out.println(user);
            System.out.println(pass);
            System.out.println("7");
            System.out.println(query);
            System.out.println("Reached login 1");


            resultSet = preparedStatement.executeQuery();
            System.out.println("Reached login 2");
            if (resultSet.next()) {
                System.out.println("A");
                return true;
            } else {
                System.out.println("B");
                return false;

            }

        } catch (Exception e) {
            return false;
            //TODO Handle Exception
        } finally {
            preparedStatement.close();
            resultSet.close();
        }

    }
}