package OrderList;

import Constructors.ItemDetails;
import Constructors.OrderDetails;
import MainMethodsAndDatabase.ConnectToDatabase;
import MainMethodsAndDatabase.MainMethods;
import alert.AlertMaker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OrderListController implements Initializable {

    @FXML
    private TableView<ItemDetails> tableID;
    @FXML
    private TableColumn<ItemDetails,Integer> iCode;
    @FXML
    private TableColumn<ItemDetails,String>iName;

    @FXML
    private TableColumn<ItemDetails,String>iDescription;

    @FXML
    private TableColumn<ItemDetails,Integer>iQuantity;




    @FXML
    private TableView<OrderDetails> tableID1;
    @FXML
    private TableColumn<OrderDetails,Integer> iCode1;

    @FXML
    private TableColumn<OrderDetails,Integer>iQuantity1;




    @FXML
    private Button btnAddToOrderList;

    @FXML
    private Button btnConfirmOrderList;

    @FXML
    private TableView<OrderDetails> Order;

    @FXML
    private TextField getItemCode;

    @FXML
    private  TextField getItemQuantity;

    MainMethods showItems;


    Connection conn = null;
    Statement stmt = null;



    private ConnectToDatabase dc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dc=new ConnectToDatabase();

        // display the list of item in the table view of the orderItem view.
        // creating an object
        showItems= new MainMethods();
        showItems.displayItemList(tableID,iCode,iName,iQuantity);

    }

    public void addToOrderList() throws Exception {

        try {

            conn = dc.Connect();
            System.out.print("connect");
            stmt = conn.createStatement();


            int test = 0;
            Boolean flag = getItemCode.getText().isEmpty() || getItemQuantity.getText().isEmpty()
                    || (Integer.parseInt(getItemQuantity.getText()) < 0);
            if (flag) {
                AlertMaker.showErrorMessage("Error", "Please Enter valid value in all fields");

            } else {
                ResultSet rs = conn.createStatement().executeQuery("SELECT ItemCode FROM itemdetails");
                while (rs.next()) {
                    int itemCode1 = Integer.parseInt(getItemCode.getText());
                    int itemQuantity1 = Integer.parseInt(getItemQuantity.getText());

                    if (rs.getInt(1) == itemCode1) {

                        System.out.print(rs.getInt(1));

                        MainMethods add = new MainMethods();
                        add.addItemsToOrderList(itemCode1, itemQuantity1);
                        showItems.displayOrderlist(tableID1, iCode1, iQuantity1);
                        getItemCode.clear();
                        getItemQuantity.clear();
                        test = 1;

                        break;
                    }

                }
            }

            if (test == 0) {
                AlertMaker.showErrorMessage("Item Code Does not exist", "Please Enter valid Item Code");

            }
        }catch (Exception Ex){
            System.out.println("Error:"+Ex);
        }
    }


    public void confirmOrderList() throws SQLException {
        int OrderID=0;
        Connection conn = dc.Connect();
        System.out.print("connect");
        try{
            ResultSet rs = conn.createStatement().executeQuery("SELECT OrderID FROM `order` ");
            System.out.println("a"+OrderID);
            while (rs.next()){
                System.out.println("order.order id "+OrderID);
                OrderID=rs.getInt(1);
            };
            OrderID = OrderID+1;
            System.out.print("v"+OrderID);

        }

        catch (SQLException e){
            System.out.println("no data in the database"+e);
            OrderID=OrderID+1;
            System.out.println("z"+OrderID);
        }

        String sql = "insert into `order`(OrderID,OrderDate) values(?,Current_Timestamp)";

        java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, OrderID);
        pStmt.executeUpdate();


        MainMethods confirmOrderList=new MainMethods();
        confirmOrderList.addToOrderDetails(OrderID);
        ResultSet rs2 = conn.createStatement().executeQuery("select itemdetails.ItemCode,itemdetails.ItemName ,orderdetails.ItemQuantity\n" +
                "from itemdetails \n" +
                "left  Join orderdetails on (itemdetails.ItemCode=orderdetails.ItemCode)\n" +
                " where orderdetails.OrderID="+OrderID+";");
        while (rs2.next()) {

            String sql1 = "INSERT INTO checkdelivery(OrderID, ItemCode,ItemName,ItemOrderedQuantity)" +
                    "VALUES (?,?,?,?)";

            java.sql.PreparedStatement pStmt1 = conn.prepareStatement(sql1);
            pStmt1.setInt(1, OrderID);
            pStmt1.setInt(2,rs2.getInt(1));
            pStmt1.setString(3, rs2.getString(2));
            pStmt1.setInt(4, rs2.getInt(3));
            pStmt1.executeUpdate();
        }


        AlertMaker.showSimpleAlert("Order Successfully added", "Order Details Saved");
        Stage stage = (Stage) btnConfirmOrderList.getScene().getWindow();
        stage.close();










    }
    public void checkItemCode() throws Exception{
        int itemCode1=Integer.parseInt(getItemCode.getText());
        Connection conn = dc.Connect();
        System.out.print("connect");
        Statement stmt = conn.createStatement();
        ResultSet rs = conn.createStatement().executeQuery("SELECT ItemCode FROM initialorderlist");
        while (rs.next()) {
            if (rs.getInt(1)==itemCode1) {
                System.out.print('1');
            }
            else{
                JOptionPane.showMessageDialog(null, "Enter valid ItemCode", "", JOptionPane.ERROR_MESSAGE);

                }


            }
        }
    }











