package system.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import system.models.JdbcSQLiteConnection;

public class ConfirmAddingController{

    @FXML
    private Button confirmButton;

    @FXML
    private Label busNumberLabel;

    @FXML
    private Label sourceLabel;

    @FXML
    private Label destinationLabel;

    @FXML
    private TextArea busStopArea;

    private ObservableList<String> busStops;
    private String source;
    private String destination;
    private int busNumber;

    public void setContent(ObservableList<String> busStops, String source, String destination, int busNumber) {
        this.busStops = busStops;
        this.source = source;
        this.destination = destination;
        this.busNumber = busNumber;
        this.busStops = busStops;
        busNumberLabel.setText(busNumber + "");
        sourceLabel.setText(source.split(", ")[0]);
        destinationLabel.setText(destination);
        String busStop = "";
        for (int i = 0; i < busStops.size(); i++) {
            busStop = busStop + busStops.get(i).split(", ")[0] + ", ";
        }
        busStopArea.setText(busStop);
        busStopArea.setEditable(false);

    }

    @FXML
    public void onConfirmButtonHandler(ActionEvent event) {
        JdbcSQLiteConnection sqLiteConnection = new JdbcSQLiteConnection();
        sqLiteConnection.insertBusRecord(busNumber, source.split(", ")[0], destination);
        String[] busStopArr = source.split(", ");
        String busStopName = busStopArr[0];
        String otherName = busStopArr[1].substring(0, busStopArr[1].length());
        String location = busStopArr[2];
        sqLiteConnection.insertBusStopRecord(busStopName, otherName, location);
        sqLiteConnection.insertStoppingRecord(busNumber, busStopName, 1);
        int count = 2;
        for (int i = 0; i < busStops.size(); i++) {
            busStopArr = busStops.get(i).split(", ");
            busStopName = busStopArr[0];
            otherName = busStopArr[1].substring(0, busStopArr[1].length());
            location = busStopArr[2];
            sqLiteConnection.insertStoppingRecord(busNumber, busStopName, count);
            sqLiteConnection.insertBusStopRecord(busStopName, otherName, location);
            count++;
        }

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

    }

}
