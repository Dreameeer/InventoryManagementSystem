package addUser;

import alert.AlertMaker;
import database.DatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import login.LoginController;
import login.LoginModel;
import main.MainController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addUserController implements Initializable {

    DatabaseHandler handler;

    @FXML
    private TextField textUserName;

    @FXML
    private Button AddUser;

    @FXML
    private CheckBox authorizedCheckbox;

    @FXML
    private TextField textUserId;


    @FXML
    private PasswordField textPass;

    @FXML
    private PasswordField rePasstext;

    @FXML
    void setTextUserId(KeyEvent event) {
        textUserId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[0-9]*")) {
                    textUserId.setText(oldValue);
                }

            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = DatabaseHandler.getInstance();
    }

    @FXML
    void AddUser(ActionEvent event) {
        String mID = textUserId.getText();
        String mUserName = textUserName.getText();
        String mUserPass = textPass.getText();
        String mRePass = rePasstext.getText();
        String UserName = LoginController.getUserName();
        String TextPass = LoginController.getTextPass();

        try {
            if (LoginModel.isAuthorized(UserName, TextPass)) {
                System.out.println(1);
                System.out.println(UserName);
                System.out.println(TextPass);
                Boolean flag = mID.isEmpty() || mUserName.isEmpty() || mUserPass.isEmpty() || mRePass.isEmpty();
                if (flag) {
                    AlertMaker.showErrorMessage("Cant add Item", "Please Enter in all fields.");
                    return;
                } else if (mRePass == mUserPass) {
                    AlertMaker.showErrorMessage("Password doesn't match", "The re-entered password doesn't match the initial password.");
                } else if (authorizedCheckbox.isSelected()) {
                    System.out.println("Selected Entry");
                    String st1 = "insert into `authorized list` (`User ID`, `User Name`, `User Password`) VALUES ("
                            + "'" + mID + "',"
                            + "'" + mUserName + "',"
                            + "'" + mUserPass + "')"
                            + "on duplicate key update `User ID` ="
                            + "'" + mID + "'";
                    String st2 = "insert into admin_list(`Admin ID`, `Admin UserName`, `Admin Password`) VALUES ("
                            + "'" + mID + "',"
                            + "'" + mUserName + "',"
                            + "'" + mUserPass + "')"
                            + "on duplicate key update `Admin ID` ="
                            + "'" + mID + "'";
                    System.out.println(st1);
                    System.out.println(st2);

                    if (handler.execAction(st1)) {
                        System.out.println("Passed");
                        handler.execAction(st2);
                        textUserName.setText("");
                        textUserId.setText("");
                        textPass.setText("");
                        rePasstext.setText("");

                        AlertMaker.showSimpleAlert("Succesfull ", "The user is successfully added as an authorized administrator");

                        ((Stage) textUserName.getScene().getWindow()).close();



                    } else {
                        AlertMaker.showErrorMessage("Error ", "The User ID needs to be integer.");

                    }
                } else {
                    String st3 = "insert into `authorized list` (`User ID`, `User Name`, `User Password`) VALUES ("
                            + "'" + mID + "',"
                            + "'" + mUserName + "',"
                            + "'" + mUserPass + "')"
                            + "on duplicate key update `User ID` ="
                            + "'" + mID + "'";
                    System.out.println(st3);

                    if (handler.execAction(st3)) {
                        textUserName.setText("");
                        textUserId.setText("");
                        textPass.setText("");
                        rePasstext.setText("");

                        AlertMaker.showSimpleAlert("Succesfull ", "The user is successfully added as an authorized user.");
                        ((Stage) textUserName.getScene().getWindow()).close();

                    } else {
                        AlertMaker.showErrorMessage("Error ", "The User ID needs to be integer.");

                    }
                }
            } else {
                AlertMaker.showErrorMessage("Not Authorized", "You are not authorized to make this action.");
                textUserName.setText("");
                textUserId.setText("");
                textPass.setText("");
                rePasstext.setText("");

            }

        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Not Authorized", "You are not authorized to make this action.");
            textUserName.setText("");
            textUserId.setText("");
            textPass.setText("");
            rePasstext.setText("");
            e.printStackTrace();
        }

    }

}
