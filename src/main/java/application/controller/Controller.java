package application.controller;

import application.models.Database;
import application.views.DisplayPath;
import application.views.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Controller implements EventHandler<ActionEvent>{

    public Parent setMainView(){
        MainView mainView = new MainView();
        Database db = mainView.getDisplayPath().getDatabase();

        mainView.getNo29button().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                DisplayPath display = new DisplayPath();
                mainView.getDrawingBusLine().setContent(display.buildView("29"));
            }
        });

        mainView.getNo134button().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                DisplayPath display = new DisplayPath();
                mainView.getDrawingBusLine().setContent(display.buildView("134"));
            }
        });

        mainView.getNo191button().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                DisplayPath display = new DisplayPath();
                mainView.getDrawingBusLine().setContent(display.buildView("191"));
            }
        });

        mainView.getNo510button().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                DisplayPath display = new DisplayPath();
                mainView.getDrawingBusLine().setContent(display.buildView("510"));
            }
        });

        mainView.getNo555button().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                DisplayPath display = new DisplayPath();
                mainView.getDrawingBusLine().setContent(display.buildView("555"));
            }
        });

        mainView.getChoice().setOnAction(e -> {
            mainView.getSearchResult().getChildren().clear();
            String busstop = mainView.getChoice().getValue().toString();
            Text busNameText = new Text();
            busNameText.setStyle("-fx-font-size: 25;-fx-font-weight:bold;");
            busNameText.setText("  "+busstop+"\n");
            mainView.getSearchResult().getChildren().add(busNameText);

            ArrayList<String> busNoList = db.selectData("SELECT bus_number FROM stopping WHERE busStop_name = '"+busstop+"'","bus_number");
            for(String s : busNoList){
                Text busNoText = new Text();
                busNoText.setStyle("-fx-font-size: 30;-fx-font-weight:normal;");
                busNoText.setFill(Color.rgb(212, 33, 90));
                busNoText.setText("  "+s);

                ArrayList<String> source = db.selectData("Select source from Bus where bus_number = "+s,"source");
                ArrayList<String> destination = db.selectData("Select destination from Bus where bus_number = "+s,"destination");

                Text souceAndDesText = new Text("\n  ( "+source.get(0)+" , "+destination.get(0)+" )\n");
                souceAndDesText.setStyle("-fx-font-size: 19;-fx-font-weight:normal;");
                souceAndDesText.setFill(Color.rgb( 33, 47, 61));

                mainView.getSearchResult().getChildren().addAll(busNoText,souceAndDesText);
            }
        });

        Parent parent = mainView.buildView();
        return parent;
    }
    @Override
    public void handle(ActionEvent event){

    }
}
