package main;

import database.DatabaseHandler;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        System.out.println("1");
        Image image = new Image("Imported.jpg");
        stage.getIcons().add(image);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(("login/login.fxml")));
        System.out.println("2");
        Scene scene = new Scene(root);
        System.out.println("3");
        stage.setScene(scene);
        System.out.println("4");
        stage.setTitle("Inventory Management System");
        System.out.println("5");
        stage.show();
        System.out.println("6");

       new Thread(() -> {
          DatabaseHandler.getInstance();
       }).start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
