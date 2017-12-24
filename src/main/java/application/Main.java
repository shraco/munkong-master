package application;

import application.controller.Controller;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import application.models.Database;

public class Main extends Application {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Controller controller = new Controller();
        Parent root = controller.setMainView();
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("NewStyle.css");

        window.setTitle("Munkong Trasport");
        window.setScene(scene);
        window.show();




    }


    public static void main(String[] args) {
        launch(args);

    }
}
