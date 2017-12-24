package system.controller;

import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<String>{

    @Override
    protected void updateItem(String string, boolean empty) {
        super.updateItem(string, empty);
        if (!empty && !string.equals(null)) {
            StoppingCellController stoppingCellController = new StoppingCellController();
            stoppingCellController.setStopLabel(string);
            setGraphic(stoppingCellController.getAnchorPane());
        }
    }

}
