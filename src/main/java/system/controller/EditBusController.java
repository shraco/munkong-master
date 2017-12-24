package system.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import system.models.*;

import java.io.IOException;

public class EditBusController {

    @FXML
    private TextField busNoField;
    @FXML
    private TextField sourceField;
    @FXML
    private TextField destinationField;
    @FXML
    private TableView stopTable;
    @FXML
    private TableColumn orderColumn;
    @FXML
    private TableColumn stopColumn;
    @FXML
    private Button backButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button moveUpButton;
    @FXML
    private Button moveDownButton;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;

    private Bus bus;

    private String id;

    private JdbcSQLiteConnection connection;

    private ObservableList<Stopping> stoppingList;

    private Stopping afterEditStopping;

    private FXMLLoader thisPageLoader;

    private int oldBusNumber;

    public void setAfterEditStopping(Stopping afterEditStopping) {
        this.afterEditStopping = afterEditStopping;
    }

    public void setUp(Bus bus, JdbcSQLiteConnection connection, String id, FXMLLoader fxmlLoader) {
        this.id = id;
        this.bus = bus;
        thisPageLoader = fxmlLoader;
        busNoField.setText(String.valueOf(bus.getBusNo()));
        sourceField.setText(bus.getSource());
        destinationField.setText(bus.getDestination());

        oldBusNumber = bus.getBusNo();

        this.connection = connection;
        stoppingList = connection.getStoppingList(bus.getBusNo());

        orderColumn.setCellValueFactory(new PropertyValueFactory<Stopping, Integer>("order"));
        orderColumn.setStyle( "-fx-alignment: CENTER;");

        stopColumn.setCellValueFactory(new PropertyValueFactory<Stopping, String>("busStopName"));

        stopTable.setItems(stoppingList);

    }

    @FXML
    public void onBackButtonHandler(ActionEvent event) {
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
        stage.setTitle("Show Bus info");
        ShowBusInfoController controller = loader.<ShowBusInfoController>getController();
        controller.setThisPageLoader(loader);
        controller.setId(id);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void onMainMenuButtonHandler(ActionEvent event) {
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

    @FXML
    public void onDeleteButtonClicked(ActionEvent event) {
        if (stopTable.getSelectionModel().getSelectedItem() != null) {
            Stopping stopping = (Stopping) stopTable.getSelectionModel().getSelectedItem();
            stoppingList.remove(stopping);
            for (int i = 0; i < stoppingList.size(); i++) {
                stoppingList.get(i).setOrder(i + 1);
            }

            sourceField.setText(stoppingList.get(0).getBusStopName());
            destinationField.setText(stoppingList.get(stoppingList.size() - 1).getBusStopName());
        }
        else {
            //...
        }
    }

    @FXML
    public void onMoveUpButtonClicked(ActionEvent event) {
        if (stopTable.getSelectionModel().getSelectedItem() != null) {
            Stopping stopping = (Stopping) stopTable.getSelectionModel().getSelectedItem();
            if (stoppingList.indexOf(stopping) - 1 >= 0) {
                int index = stoppingList.indexOf(stopping) - 1;
                stoppingList.remove(stopping);
                stoppingList.add(index, stopping);
            }
            for (int i = 0; i < stoppingList.size(); i++) {
                stoppingList.get(i).setOrder(i + 1);
            }
            sourceField.setText(stoppingList.get(0).getBusStopName());
            destinationField.setText(stoppingList.get(stoppingList.size() - 1).getBusStopName());
        }
    }

    @FXML
    public void onMoveDownButtonClicked(ActionEvent event) {
        if (stopTable.getSelectionModel().getSelectedItem() != null) {
            Stopping stopping = (Stopping) stopTable.getSelectionModel().getSelectedItem();
            if (stoppingList.indexOf(stopping) < stoppingList.size() - 1) {
                int index = stoppingList.indexOf(stopping) + 1;
                stoppingList.remove(stopping);
                stoppingList.add(index, stopping);
            }
            for (int i = 0; i < stoppingList.size(); i++) {
                stoppingList.get(i).setOrder(i + 1);
            }
            sourceField.setText(stoppingList.get(0).getBusStopName());
            destinationField.setText(stoppingList.get(stoppingList.size() - 1).getBusStopName());
        }
    }

    @FXML
    public void onEditButtonClicked(ActionEvent event) {
        if (stopTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../editstop.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("edit stop");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            EditStopController controller = loader.<EditStopController>getController();
            Stopping stopping = (Stopping) stopTable.getSelectionModel().getSelectedItem();
            controller.setCallerLoader(thisPageLoader);
            controller.setStopping(stopping);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    afterEditStopping = stopping;
                }
            });
            stage.showAndWait();
            int index = stoppingList.indexOf(stopping);
            stoppingList.remove(stopping);
            stoppingList.add(index, afterEditStopping);
            sourceField.setText(stoppingList.get(0).getBusStopName());
            destinationField.setText(stoppingList.get(stoppingList.size() - 1).getBusStopName());
        }
    }

    @FXML
    public void onAddButtonClicked(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../addEditStop.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("add stop");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        AddEditBusController controller = loader.<AddEditBusController> getController();
        controller.setCallerLoader(thisPageLoader);
        controller.setStopping(new Stopping(Integer.parseInt(busNoField.getText()), new BusStop("", "", ""), stoppingList.size() + 1));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                afterEditStopping = null;
            }
        });
        stage.showAndWait();
        if (afterEditStopping != null) {
            stoppingList.add(afterEditStopping);
            sourceField.setText(stoppingList.get(0).getBusStopName());
            destinationField.setText(stoppingList.get(stoppingList.size() - 1).getBusStopName());
        }
    }

    @FXML
    public void onSubmitButtonClicked(ActionEvent event) {
        connection.deleteBusStop(oldBusNumber);
        connection.deleteStopping(oldBusNumber);
        for (Stopping stopping: stoppingList) {
            connection.insertBusStopRecord(stopping.getBusStop().getName()
                    , "(" + stopping.getBusStop().getOtherName() + ")"
                    , stopping.getBusStop().getLocation());
            connection.insertStoppingRecord(oldBusNumber, stopping.getBusStopName()
                    , stopping.getOrder());
        }

        bus.setBusNo(Integer.parseInt(busNoField.getText()));
        bus.setSource(sourceField.getText());
        bus.setDestination(destinationField.getText());
        connection.updateBusRecord(bus, oldBusNumber);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../finishEdit.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("successful");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        FinishEditController controller = loader.<FinishEditController> getController();
        controller.setId(id);
        Stage callerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setCallerStage(callerStage);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
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
                ShowBusInfoController controller = loader.<ShowBusInfoController>getController();
                controller.setThisPageLoader(loader);
                controller.setId(id);
                callerStage.setResizable(false);
                callerStage.show();
            }
        });
        stage.showAndWait();
    }
}
