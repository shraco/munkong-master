package application.views;

import application.models.Database;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import application.models.Database;
import java.util.ArrayList;

public class DisplayPath {
    protected Database database;
    protected ArrayList<String> busName,source,destination;
    protected Pane drawingPane;

    public DisplayPath(){
        this.database = new Database() ;
        this.drawingPane = new Pane();
        this.drawingPane.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    }

    public Pane buildView(String busNo) {
        //go to get bus' s all bus stop in database
        source = database.selectData("Select source from Bus where bus_number = "+busNo,"source");
        destination = database.selectData("Select destination from Bus where bus_number = "+busNo,"destination");
        busName = database.selectData("Select * from stopping WHERE bus_number = "+busNo+" ORDER BY order_sequence;","busStop_name");
        drawingPane.setPrefSize(800,(busName.size()*100)+100);

        Text titleText = new Text(busNo);
        titleText.setFill(Color.rgb(99, 57, 116));
        titleText.setX(20);
        titleText.setY(60);
        titleText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));

        String sourceAndDes = String.format("( %s , %s )",source.get(0),destination.get(0));
        Text sourceAndDesText = new Text(sourceAndDes);
        sourceAndDesText.setFill(Color.rgb(99, 57, 116));
        sourceAndDesText.setX(120);
        sourceAndDesText.setY(60);
        sourceAndDesText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        Line line = new Line(50,200,50,(busName.size()*100));
        line.setStrokeWidth(20);

        drawingPane.getChildren().addAll(line,titleText,sourceAndDesText);
        int y = 150 ;
        for(String bus : busName) {
            Text busText = new Text(bus);
            busText.setStrokeWidth(3);
            busText.setX(150);
            busText.setY(y);
            busText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

            Circle c = new Circle(50, y, 40);
            c.setFill(Color.YELLOW);
            c.setStrokeWidth(6);
            if(y == 150 || y == busName.size()*100+50){
                c.setStroke(Color.rgb(  231, 76, 60 ));
                busText.setFill(Color.rgb(  231, 76, 60 ));
            }else{
                c.setStroke(Color.rgb(  34, 153, 84 ));
                busText.setFill(Color.rgb(   34, 153, 84
                ));
            }

            drawingPane.getChildren().addAll(c,busText);
            y = y + 100;
        }
        return drawingPane;
    }
        public Database getDatabase(){ return database; }
    }