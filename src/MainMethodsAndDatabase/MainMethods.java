package MainMethodsAndDatabase;

import Constructors.ItemDetails;
import Constructors.ItemDetailswithLocation;
import Constructors.OrderDetails;
import MainMethodsAndDatabase.ConnectToDatabase;
import alert.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import addVendor.addVendorController.VendorDetails;
import order.orderController;


import java.sql.*;

public class MainMethods {

    private ObservableList<ItemDetails> data;
    private ObservableList<OrderDetails> data1;
    private ObservableList<orderController.Order> data2;
    public ConnectToDatabase dc=new ConnectToDatabase();
    Connection conn = null;
    Statement stmt = null;


    public  void displayItemList(TableView tableID, TableColumn iCode,TableColumn iName, TableColumn iQuantity){
        try {
            Connection conn = dc.Connect();
            System.out.print("Connected to the Database");


            data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM itemdetails");

            while (rs.next()) {
                data.add(new ItemDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        iCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        iName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));

        iQuantity.setCellValueFactory(new PropertyValueFactory<>("ItemQuantity"));
        tableID.setItems(null);
        tableID.setItems(data);

    }
    public  void displayItemList(TableView tableID, TableColumn iCode,TableColumn iName){
        try {
            Connection conn = dc.Connect();
            System.out.print("Connected to the Database");
            data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM itemdetails");

            while (rs.next()) {
                data.add(new ItemDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        iCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        iName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tableID.setItems(null);
        tableID.setItems(data);

    }

    public  void displayItemList(TableView tableID, TableColumn iCode,TableColumn iName, TableColumn iDescription,TableColumn iQuantity){
        try {
            Connection conn = dc.Connect();
            data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM itemdetails");

            while (rs.next()) {
                data.add(new ItemDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        iCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        iName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        iDescription.setCellValueFactory(new PropertyValueFactory<>("ItemDescription"));
        iQuantity.setCellValueFactory(new PropertyValueFactory<>("ItemQuantity"));
        tableID.setItems(null);
        tableID.setItems(data);

    }



    public void displayOrderlist(TableView tableID1, TableColumn iCode, TableColumn iQuantity) throws NullPointerException{

        try {
            Connection conn = dc.Connect();
            data1 = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM initialorderlist");


            while (rs.next()) {
                System.out.println("Item Code is:"+rs.getInt(1));
                System.out.println("Item Code is:"+rs.getInt(2));
                data1.add(new OrderDetails(rs.getInt(1), rs.getInt(2)));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        iCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        iQuantity.setCellValueFactory(new PropertyValueFactory<>("ItemQuantity"));

        tableID1.setItems(null);
        tableID1.setItems(data1);

    }


    public void addItemsToDatabase(int ItemCode, String ItemName, String ItemDescription, int ItemQuantity) throws Exception {

            conn = dc.Connect();
            System.out.print("connect");
            stmt = conn.createStatement();

            String sql = "INSERT INTO itemdetails(ItemCode, ItemName,ItemDescription,ItemQuantity)" +
                    "VALUES (?,?,?,?)";

            java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, ItemCode);
            pStmt.setString(2,ItemName);
            pStmt.setString(3, ItemDescription);
            pStmt.setInt(4, ItemQuantity);
            pStmt.executeUpdate();
             AlertMaker.showSimpleAlert("Items Added", "Saved");
    }

    public  void addItemsToOrderList(int ItemCode,int ItemQuantity) throws Exception,SQLException{
        conn = dc.Connect();
        System.out.print("connect");
        stmt = conn.createStatement();


        ResultSet rs = conn.createStatement().executeQuery("SELECT ItemCode FROM initialorderlist");
        while (rs.next()) {
            if (rs.getInt(1)==ItemCode){
                System.out.print(rs.getInt(1));

                System.out.print("Delete");

                String query = "DELETE FROM initialorderlist where ItemCode = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1, ItemCode);
                preparedStmt.execute();

            }
        }
        String sql = "INSERT INTO initialorderlist(ItemCode,ItemQuantity)" +
                "VALUES (?,?)";
        java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);

        pStmt.setInt(1, ItemCode);
        pStmt.setInt(2, ItemQuantity);
        pStmt.executeUpdate();
    }


    public void addToOrderDetails(int OrderID) throws SQLException {

        try {
            Connection conn = dc.Connect();
            stmt = conn.createStatement();

            int ItemCode = 0;
            int ItemQuantity = 0;



            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM initialorderlist");

            while (rs.next()) {
                ItemCode = rs.getInt(1);
                ItemQuantity = rs.getInt(2);
                String sql = "INSERT INTO orderdetails(OrderID,ItemCode,ItemQuantity,OrderDate)" +
                        "VALUES (?,?,?,CURRENT_TIMESTAMP)";
                java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, OrderID);
                pStmt.setInt(2, ItemCode);
                pStmt.setInt(3, ItemQuantity);

                pStmt.executeUpdate();
            }
            ResultSet rs3 = conn.createStatement().executeQuery("truncate initialorderlist");
        }catch (SQLException ex){
            System.out.println("error"+ex);
        }


    }


    public void addLocation(int itemCode, String location) throws SQLException {
        conn = dc.Connect();
        System.out.print("connect");
        stmt = conn.createStatement();
        ResultSet rs = conn.createStatement().executeQuery("SELECT ItemCode FROM item_location");
        while (rs.next()) {
            if (rs.getInt(1)==itemCode){
                System.out.print(rs.getInt(1));

                System.out.print("Delete");
                String query = "DELETE FROM item_location where ItemCode = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1, itemCode);
                preparedStmt.execute();

            }
        }
        String sql = "INSERT INTO item_location(ItemCode, ItemLocation)" +
                "VALUES (?,?)";

        java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1,itemCode);
        pStmt.setString(2,location);
        pStmt.executeUpdate();

    }

    public void showLocation(TableView TableID1, TableColumn ItemCode, TableColumn ItemName, TableColumn ItemLocation) {
        try {
            Connection conn = dc.Connect();
            data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT itemdetails.ItemCode,itemdetails.ItemName,item_location.ItemLocation " +
                    "FROM itemDetails LEFT JOIN item_location On itemdetails.ItemCode=item_location.ItemCode;");

            while (rs.next()) {

                data.add(new ItemDetailswithLocation(rs.getInt(1),rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        ItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        ItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        ItemLocation.setCellValueFactory(new PropertyValueFactory<>("ItemLocation"));

        TableID1.setItems(null);
        TableID1.setItems(data);

    }

    public void displayOrder(TableView tableID1, TableColumn orderID, TableColumn orderDate) {
        try {
            try {
                Connection conn = dc.Connect();
                data2 = FXCollections.observableArrayList();


                ResultSet rs = conn.createStatement().executeQuery("select * from `order`");

                while (rs.next()) {

                    System.out.println(rs.getInt(1));

                    data2.add(new orderController.Order(rs.getInt(1), rs.getString(2)));
                }
            } catch (SQLException ex) {
                System.err.println("Error" + ex);
            }

            orderID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
            orderDate.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
            tableID1.setItems(null);
            tableID1.setItems(data2);
        }catch (Exception ex){
            System.out.println("error"+ex);
        }

    }

    public void displayVendorList(TableView tableView, TableColumn supIdCol, TableColumn supNameCol) throws Exception {
        ObservableList<VendorDetails> list = FXCollections.observableArrayList();
        try {
            Connection conn = dc.Connect();
            System.out.print("Connected to the Database");
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM supplierlist");

            while (rs.next()) {
                System.out.println(rs.getString(1));
                list.add(new VendorDetails(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
          System.err.println("Error"+ex);
        }
        supIdCol.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));
        supNameCol.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        tableView.setItems(null);
        tableView.setItems(list);
    }


}
