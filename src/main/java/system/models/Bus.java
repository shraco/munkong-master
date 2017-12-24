package system.models;

public class Bus {

    private int busNo;
    private String source;
    private String destination;

    public Bus(int busNo, String source, String destination) {
        this.busNo = busNo;
        this.source = source;
        this.destination = destination;
    }

    public int getBusNo() {
        return busNo;
    }


    public String getSource() {
        return source;
    }

    public void setBusNo(int busNo) {
        this.busNo = busNo;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

}
