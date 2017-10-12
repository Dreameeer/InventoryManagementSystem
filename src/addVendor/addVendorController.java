package addVendor;

import alert.AlertMaker;
import database.DatabaseHandler;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.MainController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addVendorController implements Initializable{
    DatabaseHandler handler;
    ObservableList<VendorDetails> list = FXCollections.observableArrayList();

    @FXML
    private TableView<VendorDetails > tableView;

    @FXML
    private TableColumn<?, ?> supIdCol;

    @FXML
    private TableColumn<?, ?> supNameCol;

    @FXML
    private TableColumn<?, ?> supAddCol;

    @FXML
    private TableColumn<?, ?> supPhnCol;

    @FXML
    private TextField textSupID;

    @FXML
    private TextField textSupName;

    @FXML
    private TextField textAdd;

    @FXML
    private TextField textPhone;

    @FXML
    private Button addVendorButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
        handler = DatabaseHandler.getInstance();
    }

    private void initCol() {
        supIdCol.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));
        supNameCol.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        supAddCol.setCellValueFactory(new PropertyValueFactory<>("SupplierAddress"));
        supPhnCol.setCellValueFactory(new PropertyValueFactory<>("SupplierContactNumber"));
    }

    private void loadData() {
        ObservableList<VendorDetails> list = FXCollections.observableArrayList();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM supplierlist";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                Integer SupplierID = rs.getInt("SupplierID");
                String SupplierName = rs.getString("SupplierName");
                String SupplierAddress = rs.getString("SupplierAddress");
                String SupplierContactNumber = rs.getString("SupplierContactNumber");

                list.add(new VendorDetails(SupplierID, SupplierName, SupplierAddress, SupplierContactNumber));

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().setAll(list);
    }

    @FXML
    private void addMember(ActionEvent event) {
        String mID = textSupID.getText();
        String mName = textSupName.getText();
        String mAdd = textAdd.getText();
        String mPhn = textPhone.getText();

        Boolean flag = mName.isEmpty() || mID.isEmpty() || mAdd.isEmpty() || mPhn.isEmpty();
        if (flag) {
            AlertMaker.showErrorMessage("Cant add member", "Please Enter in all fields");
            return;
        }
        String st = "INSERT INTO `inventory_management_system`.`supplierlist` (`SupplierID`, `SupplierName`, `SupplierAddress`, `SupplierContactNumber`) VALUES ("
                + "'" + mID + "',"
                + "'" + mName + "',"
                + "'" + mAdd + "',"
                + "'" + mPhn + "'"
                + ")";
        System.out.println(st);
        if (handler.execAction(st)) {
            AlertMaker.showSimpleAlert("Vendor Added", "Saved");
        } else {
            AlertMaker.showErrorMessage("Vendor cant be added", "Error Occured");
        }
        ObservableList<VendorDetails> list = FXCollections.observableArrayList();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM supplierlist";
        ResultSet rs = handler.execQuery(qu);
        loadData();
        textSupID.clear();
        textSupName.clear();
        textAdd.clear();
        textPhone.clear();

    }

    public static class VendorDetails {

        private final SimpleIntegerProperty SupplierID;
        private final SimpleStringProperty SupplierName;
        private final SimpleStringProperty SupplierAddress;

        public SimpleStringProperty supplierAddressProperty() {
            return SupplierAddress;
        }

        public void setSupplierAddress(String supplierAddress) {
            this.SupplierAddress.set(supplierAddress);
        }

        public SimpleStringProperty supplierNameProperty() {

            return SupplierName;
        }

        public void setSupplierName(String supplierName) {
            this.SupplierName.set(supplierName);
        }

        public SimpleIntegerProperty supplierIDProperty() {

            return SupplierID;
        }

        public void setSupplierID(int supplierID) {
            this.SupplierID.set(supplierID);
        }

        public SimpleStringProperty supplierContactNumberProperty() {

            return SupplierContactNumber;
        }

        public void setSupplierContactNumber(String supplierContactNumber) {
            this.SupplierContactNumber.set(supplierContactNumber);
        }

        private final SimpleStringProperty SupplierContactNumber;


        public VendorDetails(int SupplierID, String SupplierName, String SupplierAddress, String SupplierContactNumber) {
            this.SupplierID = new SimpleIntegerProperty(SupplierID);
            this.SupplierName = new SimpleStringProperty(SupplierName);
            this.SupplierAddress = new SimpleStringProperty(SupplierAddress);
            this.SupplierContactNumber = new SimpleStringProperty(SupplierContactNumber);
        }

        public Integer getSupplierID() { return SupplierID.get(); }

        public String getSupplierName() {
            return SupplierName.get();
        }

        public String getSupplierAddress() {
            return SupplierAddress.get();
        }

        public String getSupplierContactNumber() { return SupplierContactNumber.get(); }


    }
}
