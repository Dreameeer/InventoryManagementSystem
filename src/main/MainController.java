package main;

import database.DatabaseHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    DatabaseHandler databaseHandler;

    @FXML
    private MenuBar closeButton;

    @FXML
    private MenuItem addItemMenu;

    @FXML
    private MenuItem addVendorMenu;

    @FXML
    private MenuItem addAuthorizedUserMenu;

    @FXML
    private MenuItem viewItemMenu;

    @FXML
    private MenuItem viewAuthorizedUserMenu;

    @FXML
    private MenuItem viewVendorsMenu;

    @FXML
    private MenuItem viewOrderlistMenu;

    @FXML
    private MenuItem viewWasteMenu;

    @FXML
    private MenuItem viewrReportsMenu;

    @FXML
    private MenuItem aboutMenu;

    @FXML
    private Button searchItemButton;

    @FXML
    private Button viewItemDetailsButton;

    @FXML
    private Button updateInventoryButton;


    @FXML
    private Button wasteManagementButton;

    @FXML
    private Button addremoveItemButton;

    @FXML
    private Button orderListButton;

    @FXML
    private Button vendorsButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Button Location;

    @FXML
    private Button signoutButton;

    @FXML
    private Button adduser;

    @FXML
    void loadItemtable(ActionEvent event) {
        System.out.println("Button Pressed");
        loadWindow("Search/Search.fxml", "Search Item");
    }

    @FXML
    void loadVendor(ActionEvent event) {
        System.out.println("Button Pressed 1");
        loadWindow("addVendor/addVendor.fxml", "Add Vendor");
    }

    @FXML
    public void loadInventory(ActionEvent event) {
        System.out.println("Button Pressed 3");
        loadWindow("updateInventory/updateInventory.fxml", "Update Inventory");
    }

    @FXML
    public void loadAddItem(ActionEvent event) {
        System.out.println("Button Pressed 4");
        loadWindow("addDeleteItem/addDeleteItem.fxml", "Update Inventory");
    }

    @FXML
    void addWaste(ActionEvent event) {
        System.out.println("Button Pressed 5");
        loadWindow("waste/waste.fxml", "Update Inventory");
    }
    @FXML
    void orderList(ActionEvent event){
        System.out.println("BUTTON PRESSED FOR ORDERLIST");
        loadWindow("OrderList/OrderList.fxml","Ordering Window");
    }
    @FXML
    void order(ActionEvent event){
        System.out.println("BUTTON PRESSED FOR Order");
        loadWindow("order/order.fxml","Display Order");
    }

    @FXML
    void signOut(ActionEvent event) {
        System.out.println("Button Pressed 5");
        ((Node)(event.getSource())).getScene().getWindow().hide();
        loadWindow("login/login.fxml", "Inventory Management System");
        closeStage();
    }

    private void closeStage() {
        Platform.exit();
    }


    @FXML
    void location(ActionEvent event){
        System.out.println("BUTTON PRESSED FOR Location");
        loadWindow("ItemLocatiom/ItemLocation.fxml","Item Location");
    }

    @FXML
    void SupplierAndItems(ActionEvent event){
        System.out.println("BUTTON PRESSED FOR ItemsAndSupplier");
        loadWindow("ItemAndSupplier/ItemAndSupplier.fxml","Assign Item to the Supplier");
    }

    @FXML
    void CountDeliveredStock(ActionEvent event){
        System.out.println("BUTTON PRESSED FOR received delivery");
        loadWindow("checkDelivery/checkDelivery.fxml","Count Delivered Stock");
    }

    @FXML
    void adduser(ActionEvent event){
        System.out.println("BUTTON PRESSED FOR add User");
        loadWindow("addUser/addUser.fxml","Add User");
    }





    public void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getInstance();
    }
}
