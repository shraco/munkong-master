package system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FinishEditController {

    private Stage callerStage;
    private String id;

    public void setCallerStage(Stage callerStage) {
        this.callerStage = callerStage;
    }

    public void setId(String id) {
        this.id = id;
    }

    @FXML
    public void onOKButtonClicked(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../showbusinfo.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        callerStage.setScene(scene);
        callerStage.setTitle("Show Bus info");
        callerStage.setResizable(false);
        ShowBusInfoController controller = loader.<ShowBusInfoController>getController();
        controller.setThisPageLoader(loader);
        controller.setId(id);
        callerStage.show();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
