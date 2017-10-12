package checkDelivery;


import MainMethodsAndDatabase.ConnectToDatabase;
import MainMethodsAndDatabase.MainMethods;

import alert.AlertMaker;
import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import main.MainController;
import order.orderController;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class checkDeliveryController  implements Initializable{
    @FXML
    private TableView<orderController.Order> tableID1;
    @FXML
    private TableColumn<orderController.Order,Integer> OrderID;

    @FXML
    private TableColumn<orderController.Order,String>OrderDate;


    @FXML
    TextField txtItemCode;

    @FXML
    TextField txtItemQuanity;

    @FXML
    Button btnUpdate;

    @FXML
    private TableView<deliveryItems> tableID2;
    @FXML
    private TableColumn<deliveryItems,Integer> ItemsCode;

    @FXML
    private TableColumn<deliveryItems,String>ItemName;

    @FXML
    private TableColumn<deliveryItems,Integer> ItemOrderQuantity;

    @FXML
    private TableColumn<deliveryItems,Integer>DeliveredItem;

    @FXML
    private TableColumn<deliveryItems,Integer>Difference;
    @FXML
    Text OrderNumber;


    Connection conn = null;
    Statement stmt = null;
    MainMethods displayOrder;
    MainController main=new MainController();
    public int IDOfOrder;



    public ConnectToDatabase dc=new ConnectToDatabase();
    boolean click= false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayOrder= new MainMethods();
        displayOrder.displayOrder(tableID1,OrderID,OrderDate);
        displayList(IDOfOrder,tableID2,ItemsCode,ItemName,ItemOrderQuantity,
                DeliveredItem,Difference);
    }



    public void onClick(MouseEvent event) throws Exception {
        if (event.getClickCount() == 1) {
            IDOfOrder = tableID1.getSelectionModel().getSelectedItem().getOrderID();
            System.out.println("click:" + IDOfOrder);
            displayList(IDOfOrder,tableID2,ItemsCode,ItemName,ItemOrderQuantity,
                    DeliveredItem,Difference);
            OrderNumber.setText(Integer.toString(IDOfOrder));
        }
    }




    public void checkDeliveryItems() throws SQLException {

        try {

            conn = dc.Connect();
            System.out.print("connect");

            DatabaseHandler handler = DatabaseHandler.getInstance();
            int ItemOrderedQuantity = 0;

            Boolean flag = txtItemCode.getText().isEmpty() || txtItemQuanity.getText().isEmpty();

            if (flag) {
                AlertMaker.showSimpleAlert("Please enter valid Data", "Please enter valid data");

            } else {
                int itemID = Integer.parseInt(txtItemCode.getText());
                int itemDeliveredQuantity = Integer.parseInt(txtItemQuanity.getText());
                int OrderOFID = Integer.parseInt(OrderNumber.getText());

                ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM checkdelivery ");
                while (rs.next()) {

                    if ((rs.getInt(1) == OrderOFID) && (rs.getInt(2) == itemID)) {
                        ItemOrderedQuantity = rs.getInt(4);

                    }
                }
                int difference = ItemOrderedQuantity - itemDeliveredQuantity;

                String sql = "update checkdelivery set ItemReceived=?,ItemDifference=? where OrderID=? and ItemCode=?;";
                PreparedStatement preparedStmt = conn.prepareStatement(sql);
                preparedStmt.setInt(1, itemDeliveredQuantity);
                preparedStmt.setInt(2, difference);
                preparedStmt.setInt(3, OrderOFID);
                preparedStmt.setInt(4, itemID);
                preparedStmt.executeUpdate();
                displayList(IDOfOrder, tableID2, ItemsCode, ItemName, ItemOrderQuantity,
                        DeliveredItem, Difference);
                txtItemCode.clear();
                txtItemQuanity.clear();

                String sql1 = "update itemdetails set ItemQuantity=ItemQuantity +"+itemDeliveredQuantity+ " where ItemCode="+itemID+";";
                PreparedStatement preparedStmt2 = conn.prepareStatement(sql1);
                //preparedStmt2.setInt(1, itemDeliveredQuantity);
                //preparedStmt2.setInt(2, itemID);
                System.out.println(sql1);
                preparedStmt2.executeUpdate();




            }
        }catch (Exception ex){
            System.out.print("Error:"+ ex);
            AlertMaker.showSimpleAlert("Please enter valid Data", "Please enter valid data");

        }
    }




    private void displayList(int IDOfOrder,TableView tableID2,TableColumn ItemsCode,TableColumn ItemName,TableColumn ItemOrderQuantity,
                             TableColumn DeliveredItem,TableColumn Difference){


        ObservableList<deliveryItems> data1= FXCollections.observableArrayList();

        try {
            Connection conn = dc.Connect();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM checkdelivery ");
            while (rs.next()) {
                if (rs.getInt(1)==IDOfOrder) {


                        data1.add(new deliveryItems(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
                    }
                }


        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        ItemsCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        ItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        ItemOrderQuantity.setCellValueFactory(new PropertyValueFactory<>("OrderQuantity"));
        DeliveredItem.setCellValueFactory(new PropertyValueFactory<>("ReceivedQuantity"));
        Difference.setCellValueFactory(new PropertyValueFactory<>("Difference"));
        tableID2.setItems(null);
        tableID2.setItems(data1);


    }


}


