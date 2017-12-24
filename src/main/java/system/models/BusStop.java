package system.models;

public class BusStop {

    private String name;
    private String otherName;
    private String location;

    public BusStop(String name, String otherName, String location) {
        this.name = name;
        this.otherName = otherName;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getOtherName() {
        return otherName;
    }

    public String toString() {
        return name + ", (" + otherName + "), " + location;
    }

}
