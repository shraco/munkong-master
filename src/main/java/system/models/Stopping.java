package system.models;

import javafx.beans.property.SimpleStringProperty;

public class Stopping {

    private int busNo;
    private String busStopName;
    private int order;
    private BusStop busStop;

    public  Stopping(int busNo, BusStop busStop, int order) {
        this.busNo = busNo;
        this.busStopName = busStop.getName();
        this.order = order;
        this.busStop = busStop;
    }

    public int getBusNo() {
        return busNo;
    }

    public void setBusNo(int busNo) {
        this.busNo = busNo;
    }

    public String getBusStopName() {
        return busStopName;
    }


    public void setBusStopName(String busStop) {
        this.busStopName = busStop;
    }

    public int getOrder() {
        return order;
    }


    public void setOrder(int order) {
        this.order = order;
    }

    public BusStop getBusStop() {
        return busStop;
    }

    public void setBusStop(BusStop busStop) {
        this.busStop = busStop;
        setBusStopName(busStop.getName());
    }
}
