package order;

import Constructors.ItemDetails;
import Constructors.ItemDetailswithSupplier;
import MainMethodsAndDatabase.ConnectToDatabase;
import MainMethodsAndDatabase.MainMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.net.URL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class orderController implements Initializable {

    @FXML
    private TableView<Order> tableID1;
    @FXML
    private TableColumn<Order,Integer> OrderID;

    @FXML
    private TableColumn<Order,String>OrderDate;


    @FXML
    private TableView<ItemDetails> tableID2;
    @FXML
    private TableColumn<ItemDetailswithSupplier,Integer> iCode;
    @FXML
    private TableColumn<ItemDetailswithSupplier,String>iName;

    @FXML
    private TableColumn<ItemDetailswithSupplier,String>Supplier;

    @FXML
    private TableColumn<ItemDetailswithSupplier,Integer>OrderQuantity;



    MainMethods displayOrder;
    int IDOfOrder;

    public ConnectToDatabase dc=new ConnectToDatabase();
    Connection conn = null;
    Statement stmt = null;
    @Override

    public void initialize(URL location, ResourceBundle resources) {
        dc=new ConnectToDatabase();

        // display the list of item in the table view of the orderItem view.
        // creating an object
        System.out.println("OrderController");
        displayOrder= new MainMethods();
        displayOrder.displayOrder(tableID1,OrderID,OrderDate);

    }

    public void onClick(MouseEvent event) throws Exception{
        if (event.getClickCount()==1){
            System.out.print("Clicked");
            System.out.println(tableID1.getSelectionModel().getSelectedItem().getOrderID());
            IDOfOrder=tableID1.getSelectionModel().getSelectedItem().getOrderID();
            System.out.println(IDOfOrder);
            displayOrders(tableID2,iCode,iName,Supplier,OrderQuantity);

        }

    }
    public void displayOrders( TableView tableID2,TableColumn iCode,TableColumn iName,TableColumn Supplier,TableColumn OrderQuantity ) throws
    Exception{
        ObservableList<ItemDetailswithSupplier> data2= FXCollections.observableArrayList();;
        Connection conn = dc.Connect();


            ResultSet rs2 = conn.createStatement().executeQuery("select orderID from orderdetails");
            while (rs2.next()){

                System.out.println("Order Id:"+rs2.getInt(1));

                if (IDOfOrder==rs2.getInt(1)){
                    ResultSet rs = conn.createStatement().executeQuery("select itemdetails.ItemCode,itemdetails.ItemName,suppliers.SupplierName ,orderdetails.ItemQuantity\n" +
                            "from itemdetails \n" +
                            "left  Join orderdetails on (itemdetails.ItemCode=orderdetails.ItemCode)\n" +
                            "left Join suppliers on (itemdetails.ItemCode=suppliers.ItemCode) where orderdetails.OrderID="+IDOfOrder+";");
                    while (rs.next()) {
                        System.out.println(rs.getInt(1));
                        System.out.print(rs.getInt(1)+ rs.getString(2)+rs.getString(3)+ rs.getInt(4));

                        data2.add(new ItemDetailswithSupplier(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getInt(4)));




                    }

                iCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
                iName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
                Supplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
                OrderQuantity.setCellValueFactory(new PropertyValueFactory<>("ItemQuantity"));

                tableID2.setItems(null);
                tableID2.setItems(data2);
                break;

            }


            }

    }





    public static class Order{
        private int OrderID;
        private String OrderDate;


        public Order() {
            this.OrderID= 0;
            this.OrderDate = "";

        }

        public Order(int OrderID,String OrderDate) {

            this.OrderID= OrderID;
            this.OrderDate = OrderDate;

        }

        public String getOrderDate() {
            return OrderDate;
        }

        public void setOrderDate(String orderDate) {
            OrderDate = orderDate;
        }

        public int getOrderID() {

            return OrderID;
        }

        public void setOrderID(int orderID) {
            OrderID = orderID;
        }
    }
}
