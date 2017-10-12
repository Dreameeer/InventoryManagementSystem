package Search;

import Constructors.ItemDetails;
import MainMethodsAndDatabase.ConnectToDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchController implements Initializable {


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
    private Button btnLoad;

    @FXML
    private TextField SearchField;

    private ObservableList<ItemDetails>data;

    private ConnectToDatabase dc;

    @FXML    private Text Search;
    PreparedStatement preparedStatement=null;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dc=new ConnectToDatabase();
        try {
            loadDataFromDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public void loadDataFromDatabase() throws  Exception,SQLException{
        try {
            Connection conn = dc.Connect();
            System.out.print("connect");
            data = FXCollections.observableArrayList();

            String query="SELECT * FROM itemdetails where ItemName=? or ItemCode=?";
            preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1,SearchField.getText());
            preparedStatement.setString(2,SearchField.getText());
            ResultSet rs=preparedStatement.executeQuery();

            //ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM itemdetails");
            while (rs.next()) {
                System.out.print(rs.getInt(1));

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

}}
