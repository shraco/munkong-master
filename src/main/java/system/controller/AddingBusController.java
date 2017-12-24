package system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import system.models.Employee;
import system.models.JdbcSQLiteConnection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddingBusController implements Initializable{

    @FXML
    private Button backButton;

    @FXML
    private Button addButton;

    @FXML
    private ListView listView;

    @FXML
    private Label sourceLabel;

    @FXML
    private TextField sourceField;

    @FXML
    private TextField destinationField;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField busNumberField;

    private String source;

    private String id;

    private ObservableList<String> busStopsList;

    private FXMLLoader thisLoader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        busStopsList = FXCollections.observableArrayList();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLoader(FXMLLoader loader) {
        thisLoader = loader;
    }

    public void setObservableList(ObservableList<String> list) {
        busStopsList = list;
        String[] splitStr = busStopsList.get(busStopsList.size() - 1).split(", ");
        destinationField.setText(splitStr[0]);
    }

    public void setSource(String source) {
        this.source = source;
        sourceLabel.setText(source);
        sourceField.setText(source.split(", ")[0]);
    }

    @FXML
    public void onAddButtonHandler(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../addingstop.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Adding Stop");
        AddingStopController controller = loader.<AddingStopController>getController();
        controller.setObservableList(busStopsList);
        controller.setLoader(thisLoader);
        stage.showAndWait();
        listView.setItems(busStopsList);
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListViewCell();
            }
        });
    }

    @FXML
    public void onBackButtonHandler(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../busmanagermain.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Bus Management");
        JdbcSQLiteConnection sqLiteConnection = new JdbcSQLiteConnection();
        Employee employee = sqLiteConnection.getEmployeeRecord(id);
        BusManagerMainController controller = loader.<BusManagerMainController>getController();
        controller.setUpContent(employee);
        stage.show();
    }

    @FXML
    public void onConfirmButtonHandler(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../addingconfirm.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ConfirmAddingController controller = loader.<ConfirmAddingController>getController();
        controller.setContent(busStopsList, source, destinationField.getText(), Integer.parseInt(busNumberField.getText()));
        stage.showAndWait();
    }

}
