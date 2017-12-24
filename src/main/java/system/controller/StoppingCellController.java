package system.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StoppingCellController {

    @FXML
    private Label stopLabel;

    @FXML
    private AnchorPane anchorPane;

    public StoppingCellController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../stoppingcell.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void setStopLabel(String string) {
        stopLabel.setText(string);
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
