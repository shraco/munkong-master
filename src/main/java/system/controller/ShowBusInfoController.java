package system.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import system.models.JdbcSQLiteConnection;
import system.models.Bus;
import system.models.Employee;

import java.io.IOException;

public class ShowBusInfoController {

    @FXML
    private TableView showBusInfoTable;
    @FXML
    private TableColumn busNoColumn;
    @FXML
    private TableColumn sourceColumn;
    @FXML
    private TableColumn destinationColumn;
    @FXML
    private TextField busNoField;
    @FXML
    private TextField sourceField;
    @FXML
    private TextField destinationField;
    @FXML
    private Button editButton;
    @FXML
    private Button backButton;

    private FXMLLoader thisPageLoader;

    private ObservableList<Bus> busList;

    private JdbcSQLiteConnection connection;

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public void setThisPageLoader(FXMLLoader thisPageLoader) {
        this.thisPageLoader = thisPageLoader;
        connection = new JdbcSQLiteConnection();
        busList = connection.getBusList();
        if (busList != null) {
            busNoColumn.setCellValueFactory(new PropertyValueFactory<Bus, Integer>("busNo"));
            busNoColumn.setStyle( "-fx-alignment: CENTER;");

            sourceColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("source"));
            destinationColumn.setCellValueFactory(new PropertyValueFactory<Bus, String>("destination"));

            showBusInfoTable.setItems(busList);
            showBusInfoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    Bus bus = (Bus) newValue;
                    if (bus != null) {
                        busNoField.setText(String.valueOf(bus.getBusNo()));
                        sourceField.setText(bus.getSource());
                        destinationField.setText(bus.getDestination());
                    }
                }
            });
        }
    }

    @FXML
    public void onDeleteBusButtonHandler(ActionEvent event) {
        if (showBusInfoTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../deleteconfirm.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("confirm delete");
            DeleteConfirmController controller = loader.<DeleteConfirmController> getController();
            controller.setBusList(busList);
            controller.setConnection(connection);
            controller.setBus((Bus) showBusInfoTable.getSelectionModel().getSelectedItem());
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../unselectedbusnumber.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("error message");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    @FXML
    public void onEditButtonHandler(ActionEvent event) {
        if (showBusInfoTable.getSelectionModel().getSelectedItem() != null) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../editbus.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("edit bus");
            EditBusController controller = loader.<EditBusController> getController();
            controller.setUp((Bus) showBusInfoTable.getSelectionModel().getSelectedItem(), connection, id, loader);
            stage.setResizable(false);
            stage.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../unselectedbusnumber.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("error message");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
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
        Employee employee = connection.getEmployeeRecord(id);
        BusManagerMainController controller = loader.<BusManagerMainController>getController();
        controller.setUpContent(employee);
        stage.setResizable(false);
        stage.show();
    }
}
