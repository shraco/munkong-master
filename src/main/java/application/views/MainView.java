package application.views;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;


public class MainView {
    DisplayPath displayPath = new DisplayPath();

    protected Button no510button = new Button("สาย 510");
    protected Button no29button = new Button("สาย 29");
    protected Button no134button = new Button("สาย 134");
    protected Button no555button = new Button("สาย 555");
    protected Button no191button = new Button("สาย 191");
    protected Label chooseBusNoLabel = new Label("เลือกสายรถเมล์");
    protected VBox buttonLayout = new VBox(15);
    protected BorderPane backLayout = new BorderPane();
    protected Label searchLabel = new Label("ค้นหาสายรถเมล์จากป้าย");
    protected GridPane chooseBusNoLayout = new GridPane();
    protected GridPane searchLayout = new GridPane();
    protected TextFlow searchResult = new TextFlow();
    protected ComboBox choice = new ComboBox();
    protected ScrollPane drawingBusLine = new ScrollPane();


    public Parent buildView(){
        ArrayList<String>  options = displayPath.getDatabase().selectData("SELECT DISTINCT busStop_name FROM stopping ORDER BY busStop_name;","busStop_name");
        ObservableList<String> oListOptions = FXCollections.observableArrayList(options);
        choice.setItems(oListOptions);

        drawingBusLine.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        drawingBusLine.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        chooseBusNoLayout.setPadding(new Insets(0,10,10,10));
        chooseBusNoLayout.setVgap(20);
        chooseBusNoLayout.setHgap(30);

        //set chooseLayout
        GridPane.setConstraints(chooseBusNoLabel,0,0);
        GridPane.setConstraints(buttonLayout,0,1);
        no29button.setPrefSize(120,70);
        no134button.setPrefSize(120,70);
        no510button.setPrefSize(120,70);
        no191button.setPrefSize(120,70);
        no555button.setPrefSize(120,70);
        buttonLayout.getChildren().addAll(no29button,no134button,no191button,no510button,no555button);
        buttonLayout.setAlignment(Pos.CENTER);
        chooseBusNoLayout.getChildren().addAll(chooseBusNoLabel,buttonLayout);


        //set searchLayout
        GridPane.setConstraints(searchLabel,0,0);
        GridPane.setConstraints(choice,0,1);
        GridPane.setConstraints(searchResult,0,2);


        searchResult.setPrefSize(21,430);
        searchResult.setDisable(true);
        searchLayout.setVgap(20);
        searchLayout.getChildren().addAll(searchLabel,choice,searchResult);

        //Main Layout
        backLayout.setPadding(new Insets(10,20,10,10));
        backLayout.setLeft(chooseBusNoLayout);
        backLayout.setRight(searchLayout);
        backLayout.setCenter(drawingBusLine);

        return backLayout;

    }


    public Button getNo510button() {
        System.out.println("510");
        return no510button;
    }
    public Button getNo29button() {
        System.out.println("29");
        return no29button;
    }
    public Button getNo134button() {
        System.out.println("134");
        return no134button;
    }
    public Button getNo555button() {
        System.out.println("555");
        return no555button;
    }
    public Button getNo191button() {
        System.out.println("191");
        return no191button;
    }

    public BorderPane getBackLayout(){
        return backLayout;
    }
    public DisplayPath getDisplayPath() { return displayPath; }
    public ComboBox getChoice(){ return choice; }
    public TextFlow getSearchResult(){ return searchResult; }
    public ScrollPane getDrawingBusLine(){ return drawingBusLine; }
}
