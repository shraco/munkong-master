package system.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import system.models.Bus;
import system.models.JdbcSQLiteConnection;

public class DeleteConfirmController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Bus> busList;
    private JdbcSQLiteConnection connection;
    private Bus bus;

    public void setConnection(JdbcSQLiteConnection connection) {
        this.connection = connection;
    }

    public void setBusList(ObservableList<Bus> busList) {
        this.busList = busList;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @FXML
    public void onCancelButtonHandler(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onDeleteButtonHandler(ActionEvent event) {
        connection.deleteBus(bus);
        busList.remove(bus);

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
