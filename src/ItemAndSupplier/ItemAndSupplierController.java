package ItemAndSupplier;

import Constructors.ItemDetails;
import Constructors.SupplierAndItem;
import MainMethodsAndDatabase.ConnectToDatabase;
import MainMethodsAndDatabase.MainMethods;
import alert.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import addVendor.addVendorController.VendorDetails;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.MainController;


public class ItemAndSupplierController implements Initializable {
    @FXML
    private TableView<ItemDetails> tableID;
    @FXML
    private TableColumn<ItemDetails,Integer> iCode;
    @FXML
    private TableColumn<ItemDetails,String>iName;

    @FXML
    private TableView<VendorDetails > tableView;

    @FXML
    private TableColumn<VendorDetails,Integer> supIdCol;

    @FXML
    private TableColumn<VendorDetails,String> supNameCol;

    @FXML
    private TableView<SupplierAndItem> tableID1;
    @FXML
    private TableColumn<SupplierAndItem,Integer> iCode1;
    @FXML
    private TableColumn<SupplierAndItem,String>iName1;

    @FXML
    private TableColumn<SupplierAndItem,String> supNameCol1;


    @FXML
    private TableColumn<SupplierAndItem,Integer> supIdCol1;



    @FXML
    private TextField ItemName;
    @FXML
    private TextField SupplierName;

    @FXML
    Button btnAddItemSupplier;

    @FXML
    Button btnAddItems;

    @FXML
    Button btnAddSupplier;

    @FXML
    Button btnRefresh;


    public ConnectToDatabase dc=new ConnectToDatabase();
    Connection conn = null;
    Statement stmt = null;

    int itemCode=0;
    String itemName;
    int supplierID;
    String supplierName;
    private ObservableList<SupplierAndItem> data;

    MainMethods showItems=new MainMethods();
    MainMethods showVendors=new MainMethods();
    MainController main=new MainController();


    @FXML
    public void addItemButton(ActionEvent event){

        main.loadWindow("addDeleteItem/addDeleteItem.fxml","Update Inventory");
    }

    public void addVendor(ActionEvent event){
        main.loadWindow("addVendor/addVendor.fxml", "Add Vendor");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showItems.displayItemList(tableID,iCode,iName);

        try {
            showVendors.displayVendorList(tableView,supIdCol,supNameCol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showSupplierAndItems(tableID1,iCode1,iName1,supIdCol1,supNameCol1);


    }

    public void Refresh() throws Exception {
        showItems.displayItemList(tableID,iCode,iName);
        showVendors.displayVendorList(tableView,supIdCol,supNameCol);
        showSupplierAndItems(tableID1,iCode1,iName1,supIdCol1,supNameCol1);
        ItemName.clear();
        SupplierName.clear();


    }

    private void showSupplierAndItems(TableView tableID1,TableColumn iCode1,TableColumn iName1, TableColumn supIdCol1,TableColumn supNameCol1) {
        try {
            Connection conn = dc.Connect();
            data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM suppliers");

            while (rs.next()) {
                if(itemCode==0){
                    data.add(new SupplierAndItem(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4)));

                }
                else if(itemCode==rs.getInt(1)){
                    data.add(new SupplierAndItem(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4)));

                }

            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        iCode1.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        iName1.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        supIdCol1.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));
        supNameCol1.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        tableID1.setItems(null);
        tableID1.setItems(data);

    }

    public void onClick(MouseEvent event) {
        try{
            if (event.getClickCount()==1) {
                System.out.print("Clicked");
                System.out.println(tableID.getSelectionModel().getSelectedItem().getItemCode());
                ItemName.clear();
                ItemName.setText(tableID.getSelectionModel().getSelectedItem().getItemName());
                itemCode = tableID.getSelectionModel().getSelectedItem().getItemCode();
                itemName = tableID.getSelectionModel().getSelectedItem().getItemName();
                System.out.print(itemCode);
                System.out.print(itemName);
                showSupplierAndItems(tableID1,iCode1,iName1,supIdCol1,supNameCol1);
            }




        }catch (Exception ex){
            System.out.print("Error"+ex);
            AlertMaker.showErrorMessage("Error ", "No Items Clicked or no Item In the  list");

        }
    }
    public void onClickSupplier(MouseEvent event) throws Exception{
        try {
            if (event.getClickCount() == 1) {
                System.out.print("Clicked");
                System.out.println(tableView.getSelectionModel().getSelectedItem().getSupplierName());
                SupplierName.clear();
                SupplierName.setText(tableView.getSelectionModel().getSelectedItem().getSupplierName());
                supplierID = tableView.getSelectionModel().getSelectedItem().getSupplierID();
                supplierName = tableView.getSelectionModel().getSelectedItem().getSupplierName();

            }
        }catch (Exception ex){
            System.out.print("Error"+ex);
            AlertMaker.showErrorMessage("Error ", "No Supplier in the list or selection at empty spot");

        }
    }

    public void addItemSupplier() throws SQLException {
        if (itemName!=null && supplierName!=null) {
            conn = dc.Connect();
            System.out.print("connect");
            stmt = conn.createStatement();

            ResultSet rs = conn.createStatement().executeQuery("SELECT ItemCode, SupplierID FROM suppliers");
            boolean duplicate = false;
            while (rs.next()) {
                System.out.print(rs.getInt(1));
                if ((rs.getInt(1) == itemCode) && (rs.getInt(2) == supplierID)) {
                    duplicate = true;
                    AlertMaker.showSimpleAlert("Items Cannot be Added", "Item and the Supplier already Connected");
                    ItemName.clear();
                    SupplierName.clear();
                }

            }
            if (duplicate == false) {
                String sql = "INSERT INTO suppliers(ItemCode, ItemName,SupplierID,SupplierName)" +
                        "VALUES (?,?,?,?)";

                java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, itemCode);
                pStmt.setString(2, itemName);
                pStmt.setInt(3, supplierID);
                pStmt.setString(4, supplierName);
                pStmt.executeUpdate();
                showSupplierAndItems(tableID1,iCode1,iName1,supIdCol1,supNameCol1);
                AlertMaker.showSimpleAlert("Items Added", "Saved");
                ItemName.clear();
                SupplierName.clear();
            }
        }
        else{
            AlertMaker.showSimpleAlert("Item and Supplier Not Selected", "Please Select Item from ItemList and Supplier from SupplierList");
            ItemName.clear();
            SupplierName.clear();
        }

    }

}
