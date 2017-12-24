package system.models;

public class Employee {

    private String id;
    private String nationalID;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;

    public Employee(String id, String name, String surname, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
