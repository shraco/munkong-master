package system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import system.models.BusStop;
import system.models.Stopping;

public class EditStopController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField otherNameField;
    @FXML
    private TextField locationField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button editButton;

    private Stopping stopping;

    private FXMLLoader callerLoader;

    public void setCallerLoader(FXMLLoader callerLoader) {
        this.callerLoader = callerLoader;
    }

    public void setStopping(Stopping stopping) {
        this.stopping = stopping;
        nameField.setText(stopping.getBusStop().getName());
        otherNameField.setText(stopping.getBusStop().getOtherName());
        locationField.setText(stopping.getBusStop().getLocation());
    }

    @FXML
    public void onCancelButtonClicked(ActionEvent event) {
        EditBusController controller = callerLoader.<EditBusController>getController();
        controller.setAfterEditStopping(stopping);

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onEditButtonClicked(ActionEvent event) {
        if (nameField.getText() != null && locationField.getText() != null) {
            stopping.setBusStop(new BusStop(nameField.getText(), otherNameField.getText(), locationField.getText()));
            EditBusController controller = callerLoader.<EditBusController>getController();
            controller.setAfterEditStopping(stopping);

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        }
    }
}