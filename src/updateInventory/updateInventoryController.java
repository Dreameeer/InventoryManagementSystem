package updateInventory;

import Constructors.ItemDetails;
import addVendor.addVendorController;
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

public class updateInventoryController implements Initializable {
    DatabaseHandler handler;
    ObservableList<ItemDetails> list = FXCollections.observableArrayList();
    @FXML
    private TableView<ItemDetails> tableView;

    @FXML
    private TableColumn<ItemDetails, Integer> codeCol;

    @FXML
    private TableColumn<ItemDetails, String> nameCol;

    @FXML
    private TableColumn<ItemDetails, String> desCol;

    @FXML
    private TableColumn<ItemDetails, Integer> qtyCol;

    @FXML
    private TextField textItemcode;

    @FXML
    private TextField textItemQty;

    @FXML
    private Button addItemButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
        handler = DatabaseHandler.getInstance();

    }

    private void initCol() {
        codeCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("ItemDescription"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("ItemQuantity"));
    }

    private void loadData() {
        ObservableList<ItemDetails> list = FXCollections.observableArrayList();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM itemdetails";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                Integer ItemCode = rs.getInt("ItemCode");
                String ItemName = rs.getString("ItemName");
                String ItemDescription = rs.getString("ItemDescription");
                Integer ItemQuantity = rs.getInt("ItemQuantity");

                list.add(new ItemDetails(ItemCode, ItemName, ItemDescription, ItemQuantity));

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().setAll(list);
    }

    @FXML
    void addItem(ActionEvent event) {

        String mID = textItemcode.getText();
        String mQty = textItemQty.getText();
        Boolean flag = mID.isEmpty() || mQty.isEmpty();
        if (flag) {
            AlertMaker.showErrorMessage("Cant add Item", "Please Enter in all fields");
            return;
        }
         else{
            String st1 = "update inventory_management_system.itemdetails set ItemQuantity = ItemQuantity + "
                    + "" + mQty + " where ItemCode ="
                    + "" + mID + "";
            System.out.println(st1);
            if (handler.execAction(st1)) {
                AlertMaker.showSimpleAlert("Items Added", "Saved");
            } else {
                AlertMaker.showErrorMessage("Items cant be added", "Error Occurred");
            }
        }

        ObservableList<ItemDetails> list = FXCollections.observableArrayList();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM itemdetails";
        ResultSet rs = handler.execQuery(qu);
        loadData();
    }



}


