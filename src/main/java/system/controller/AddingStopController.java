package system.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import system.models.BusStop;

import java.net.URL;
import java.util.ResourceBundle;

public class AddingStopController implements Initializable{

    @FXML
    private TextField nameField;

    @FXML
    private TextField otherNameField;

    @FXML
    private TextField locationField;

    @FXML
    private RadioButton sourceButton;

    @FXML
    private RadioButton notSourceButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label errorNameLabel;

    @FXML
    private Label errorLocationLabel;

    private ToggleGroup toggleGroup;
    private ObservableList<String> busStopList;
    private FXMLLoader loader;
    private String source;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleGroup = new ToggleGroup();
        sourceButton.setToggleGroup(toggleGroup);
        notSourceButton.setToggleGroup(toggleGroup);
        sourceButton.setSelected(true);
    }

    public void setObservableList(ObservableList<String> list) {
        busStopList = list;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @FXML
    public void onConfirmButtonHandler(ActionEvent event) {
        String name = nameField.getText();
        String otherName = otherNameField.getText();
        String location = locationField.getText();

        if (name.equals("")) {
            errorNameLabel.setVisible(true);
        }
        else {
            errorNameLabel.setVisible(false);
        }
        if (location.equals("")) {
            errorLocationLabel.setVisible(true);
        }
        else {
            errorLocationLabel.setVisible(false);
        }
        if ((!name.equals("")) && (!location.equals(""))) {
            BusStop busStop = new BusStop(name, otherName, location);
            if (sourceButton.isSelected()) {
                source = busStop.toString();
                AddingBusController controller =
                        loader.<AddingBusController>getController();
                controller.setSource(source);
            }
            else {
                busStopList.add(busStop.toString());
                AddingBusController controller =
                        loader.<AddingBusController>getController();
                controller.setObservableList(busStopList);
            }
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        }
    }
}
