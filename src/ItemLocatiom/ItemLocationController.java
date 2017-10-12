package ItemLocatiom;

import Constructors.ItemDetailswithLocation;
import MainMethodsAndDatabase.MainMethods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemLocationController implements Initializable {
    @FXML
    private TextField getItemCode;

    @FXML
    private TextField getItemLocation;

    @FXML
    private Button AddLocation;

    @FXML
    private TableView<ItemDetailswithLocation> tableID;
    @FXML
    private TableColumn<ItemDetailswithLocation,Integer> iCode;
    @FXML
    private TableColumn<ItemDetailswithLocation,String>iName;

    @FXML
    private TableColumn<ItemDetailswithLocation,String>iLocation;


    public void locationButtonClicked() throws SQLException {

        int itemCode=Integer.parseInt(getItemCode.getText());
        String Location=getItemLocation.getText();


        MainMethods addLocation=new MainMethods();
        addLocation.addLocation(itemCode,Location);


        MainMethods showLocation=new MainMethods();
        showLocation.showLocation(tableID,iCode,iName,iLocation);




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainMethods showLocation=new MainMethods();
        System.out.print("asdsa");
        showLocation.showLocation(tableID,iCode,iName,iLocation);
    }
}
