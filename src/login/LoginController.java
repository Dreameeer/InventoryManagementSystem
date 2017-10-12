package login;

import javafx.application.Platform;
import login.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.MainController;


public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();

    private static String UserName;
    private static String TextPass;


    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField textPassword;

    @FXML
    private Label titleLabel;

    DatabaseHandler databaseHandler;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseHandler databaseHandler;
    }


    @FXML
    public void Login(ActionEvent event) {
        System.out.println("Button Pressed");
        try {
            if (LoginModel.isLogin(textUsername.getText(), textPassword.getText())) {
                setUserName(textUsername.getText());
                setTextPass(textPassword.getText());
                titleLabel.setText("Login Successfull");
                System.out.println(UserName);
                System.out.println(TextPass);
                closeStage();
                loadMain();
                System.out.println("Y");
            } else {
                titleLabel.setText("Login Unsuccessfull");

                System.out.println("Z");
                textUsername.setText("");
                textPassword.setText("");
            }
        } catch (SQLException e) {
            titleLabel.setText("Login Unsuccessfull");
            e.printStackTrace();
        }
    }


    private void closeStage() {
        ((Stage) textUsername.getScene().getWindow()).close();
    }

    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("main/Main.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(parent));
            stage.setOnCloseRequest(e -> Platform.exit());
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getUserName() {
        return UserName;
    }

    public void setUserName(String getUserName) {
        this.UserName = getUserName;
    }

    public static String getTextPass() {
        return TextPass;
    }

    public void setTextPass(String getTextPass) {
        this.TextPass = getTextPass;
    }

}

