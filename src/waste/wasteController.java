package waste;

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
import updateInventory.updateInventoryController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class wasteController implements Initializable {

    DatabaseHandler handler;
    ObservableList<WasteItem> list = FXCollections.observableArrayList();
    @FXML
    private TableView<WasteItem> tableView;

    @FXML
    private TableColumn<WasteItem, Integer> codeCol;

    @FXML
    private TableColumn<WasteItem, String> timeCol;


    @FXML
    private TableColumn<WasteItem, Integer> qtyCol;


    @FXML
    private TextField textItemcode;


    @FXML
    private TextField textItemQty;

    @FXML
    private Button addWasteButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
        handler = DatabaseHandler.getInstance();

    }

    private void initCol() {
        codeCol.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("ItemQuantity"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("WasteTime"));
    }

    private void loadData() {
        ObservableList<WasteItem> list = FXCollections.observableArrayList();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM waste";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                Integer ItemCode = rs.getInt("ItemCode");
                Integer ItemQuantity = rs.getInt("ItemQuantity");
                String WasteTime = rs.getString("WasteTime");

                list.add(new WasteItem(ItemCode, ItemQuantity, WasteTime));

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().setAll(list);
    }

    @FXML
    void addWaste(ActionEvent event) {
        String mID = textItemcode.getText();
        String mQty = textItemQty.getText();
        int Qty = Integer.parseInt(mQty);
        System.out.println(Qty);
        Boolean flag = mID.isEmpty() || mQty.isEmpty();
        Boolean records = false;
        if (flag) {
            AlertMaker.showErrorMessage("Cant add Item", "Please Enter in all fields.");
            return;
        }
        else if  (Qty <= 0) {
            AlertMaker.showErrorMessage("Cant add Item", "Item Quantity cannot be zero or remove Negative sign!");
        }
        else{
            String st1 = "insert into waste values ("
                    + "'" + mID + "',"
                    + "'" + mQty + "', CURRENT_TIMESTAMP )";

            String st2 = "update inventory_management_system.itemdetails set ItemQuantity = ItemQuantity - "
                    + "" + mQty + " where ItemCode ="
                    + "" + mID + " and ItemQuantity >="
                    + "" + mQty + "";


            String st3 = "Select * from itemdetails where ItemCode ="
                    + "'" + mID + "'";

            String st4 = "select ItemQuantity from itemdetails where ItemCode ="
                    + "'" + mID + "'";
            ResultSet rs = handler.execQuery(st3);
            System.out.println(st2);
            if (handler.execAction(st3)) {
                try {
                    while (rs.next()) {
                        records = true;
                        Integer ItemQuantity = rs.getInt("ItemQuantity");
                        if (ItemQuantity >= Qty){
                            handler.execAction(st2);
                            handler.execAction(st1);
                            AlertMaker.showSimpleAlert("Items Added To Waste", "Saved");
                        }else{
                            AlertMaker.showSimpleAlert("Item Quantity Error", "Cannot complete the process. Please enter correct value.");
                        }


                    }
                    if (!records) {
                        AlertMaker.showSimpleAlert("Item Code not found!", "We don't have a match for the Item code entered. Please check the Item Code and try again.");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                AlertMaker.showErrorMessage("Items cant be added to Waste", "Error Occurred");
            }
        }

        String qu = "SELECT * FROM waste";
        ResultSet rs = handler.execQuery(qu);
        loadData();
    }


    public static class WasteItem {

        private final SimpleIntegerProperty ItemCode;
        private final SimpleIntegerProperty ItemQuantity;
        private final SimpleStringProperty WasteTime;


        public WasteItem(int ItemCode, int ItemQuantity, String WasteTime) {
            this.ItemCode = new SimpleIntegerProperty(ItemCode);
            this.ItemQuantity = new SimpleIntegerProperty(ItemQuantity);
            this.WasteTime = new SimpleStringProperty(WasteTime);
        }

        public Integer getItemCode() {
            return ItemCode.get();
        }


        public Integer getItemQuantity() {
            return ItemQuantity.get();
        }

        public String getWasteTime() {
            return WasteTime.get();
        }


    }
}
