package system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import system.models.Employee;

import java.io.IOException;

public class BusManagerMainController {

    @FXML
    private Button logoutButton;
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Button addButton;
    @FXML
    private Button showBusButton;

    private String employeeID;

    protected void setUpContent(Employee employee) {

        idLabel.setText(employee.getId());
        nameLabel.setText(employee.getName() + "  " + employee.getSurname());
        employeeID = employee.getId();
    }

    @FXML
    public void onLogoutButtonHandler(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../../mainlogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Munkong Trasport:Official Login");
        stage.show();
    }

    @FXML
    public void onAddButtonHandler(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../addingbus.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Adding Bus");
        AddingBusController controller =
                loader.<AddingBusController>getController();
        controller.setId(employeeID);
        controller.setLoader(loader);
        stage.show();
    }

    @FXML
    public void onShowBusButtonHandler(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../showbusinfo.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Show bus info");
        ShowBusInfoController controller =
                loader.<ShowBusInfoController>getController();
        controller.setThisPageLoader(loader);
        controller.setId(employeeID);
        stage.show();
    }
}
