package Report;

import Constructors.ItemDetailswithSupplier;
import MainMethodsAndDatabase.ConnectToDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import order.orderController;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    private TableView<Report> tableID1;
    @FXML
    private TableColumn<Report,Integer> ItemCode;

    @FXML
    private TableColumn<Report,String>ItemName;

    @FXML
    private TableColumn<Report,Integer> ExpectedItemCounts;

    @FXML
    private TableColumn<Report,Integer>ItemsLost;
    @FXML
    private TableColumn<Report,Integer> ItemSentInDelivery;

    @FXML
    private TableColumn<Report,Integer>ItemQuantotyOnStock;

    public ConnectToDatabase dc=new ConnectToDatabase();
    Connection conn = null;
    Statement stmt = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            generateReport();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void generateReport() throws SQLException {
        System.out.print("called");
        int ExpectedItemCount=0;
        int ItemAddedToDelivery=0;
        int itemLost=0;
        int ItemQuantity=0;
        ObservableList<Report> data1= FXCollections.observableArrayList();;
        Connection conn = dc.Connect();
        ResultSet rs = conn.createStatement().executeQuery("select itemdetails.ItemCode,\n" +
                "itemdetails.ItemName,\n" +
                "waste.ItemQuantity,\n" +
                "waste.ItemQuantity,\n" +
                "itemdetails.ItemQuantity\n" +
                "from itemdetails\n" +
                "left join waste\n" +
                "on itemdetails.ItemCode=waste.ItemQuantity;");
        while (rs.next()){
            ItemAddedToDelivery=rs.getInt(3);
            itemLost=rs.getInt(4);
            ItemQuantity=rs.getInt(5);
            ExpectedItemCount=ItemQuantity+itemLost;
            data1.add(new Report(rs.getInt(1),rs.getString(2),ExpectedItemCount,rs.getInt(3),rs.getInt(4),rs.getInt(5)));

        }
        ItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        ItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        ExpectedItemCounts.setCellValueFactory(new PropertyValueFactory<>("expectedItemCount"));
        ItemSentInDelivery.setCellValueFactory(new PropertyValueFactory<>("itemLost"));
        ItemsLost.setCellValueFactory(new PropertyValueFactory<>("ItemSentDelivery"));
        ItemQuantotyOnStock.setCellValueFactory(new PropertyValueFactory<>("ItemQuantityInStock"));

        tableID1.setItems(null);
        tableID1.setItems(data1);


}
    public static class Report{
        int ItemCode;
        String ItemName;
        int expectedItemCount;
        int itemLost;
        int ItemSentDelivery;
        int ItemQuantityInStock;


        public Report() {
            this.ItemCode = 0;
            this.ItemName = "";
            int expectedItemCount=0;
            int itemLost=0;
            int ItemSentDelivery=0;
            int ItemQuantityInStock=0;

            }

        public int getItemQuantityInStock() {
            return ItemQuantityInStock;
        }

        public void setItemQuantityInStock(int itemQuantityInStock) {
            ItemQuantityInStock = itemQuantityInStock;
        }

        public int getItemSentDelivery() {

            return ItemSentDelivery;
        }

        public void setItemSentDelivery(int itemSentDelivery) {
            ItemSentDelivery = itemSentDelivery;
        }

        public int getItemLost() {

            return itemLost;
        }

        public void setItemLost(int itemLost) {
            this.itemLost = itemLost;
        }

        public int getExpectedItemCount() {

            return expectedItemCount;
        }

        public void setExpectedItemCount(int expectedItemCount) {
            this.expectedItemCount = expectedItemCount;
        }

        public String getItemName() {

            return ItemName;
        }

        public void setItemName(String itemName) {
            ItemName = itemName;
        }

        public int getItemCode() {

            return ItemCode;
        }

        public void setItemCode(int itemCode) {
            ItemCode = itemCode;
        }

        public Report(int ItemCode, String ItemName, int expectedItemCount, int itemLost, int ItemSentDelivery, int ItemQuantityInStock) {
            this.ItemCode = ItemCode;
            this.ItemName = ItemName;
            this.expectedItemCount=expectedItemCount;
            this.itemLost=itemLost;
            this.ItemSentDelivery=ItemSentDelivery;
            this.ItemQuantityInStock=ItemQuantityInStock;

            }
        }
}
