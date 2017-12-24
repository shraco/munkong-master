package system.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class JdbcSQLiteConnection {

    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:BusSystem.db";
        Connection connection = DriverManager.getConnection(dbURL);
        return connection;
    }

    public Employee getEmployeeRecord(String id) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                String query = "SELECT * FROM Employee WHERE employee_id = '" + id + "';";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String name = resultSet.getString(5);
                    String surname = resultSet.getString(6);
                    String nationalID = resultSet.getString(4);
                    String phoneNumber = resultSet.getString(7);
                    String email = resultSet.getString(8);
                    connection.close();
                    return new Employee(id, name, surname,phoneNumber, email);
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeRecord(String username, String password) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                String query = "SELECT * FROM Employee WHERE username = '" + username + "'AND password = '" + password + "';";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String id = resultSet.getString(1);
                    String name = resultSet.getString(5);
                    String surname = resultSet.getString(6);
                    String nationalID = resultSet.getString(4);
                    String phoneNumber = resultSet.getString(7);
                    String email = resultSet.getString(8);
                    connection.close();
                    return new Employee(id, name, surname,phoneNumber, email);
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkUsernamePassword(String username, String password) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                String query = "SELECT password FROM Employee WHERE username = '" + username + "';";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String pass = resultSet.getString(1);
                    if (password.equals(pass)) {
                        connection.close();
                        return true;
                    }
                }
                connection.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkBusRecord(int busNumber) {
        String query = "SELECT * FROM Bus WHERE bus_number = '" + busNumber + "';";
        return checkRecord(query);
    }

    public boolean checkStoppingRecord(int busNumber, String busStopName) {
        String query = "SELECT * FROM Stopping WHERE bus_number = '" + busNumber + "' AND busStop_name = '" + busStopName + "';";
        return checkRecord(query);
    }

    public boolean checkBusStopRecord(String busStopName, String location) {
        String query = "SELECT * FROM BusStop WHERE name = '" + busStopName + "' AND location = '" + location + "';";
        return checkRecord(query);
    }

    protected boolean checkRecord(String query) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    connection.close();
                    return false;
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void insertBusRecord(int busNumber, String source, String destination) {
        try {
            Connection connection = getConnection();
            String query = "INSERT INTO Bus VALUES (" + busNumber + ", '" + source + "', '" + destination + "');";
            if (connection != null) {
                Statement statement = connection.createStatement();
                if (checkBusRecord(busNumber)) {
                    statement.executeUpdate(query);
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void insertStoppingRecord(int busNumber, String stop, int order) {
        try {
            Connection connection = getConnection();
            String query = "INSERT INTO Stopping VALUES (" + busNumber + ", '" + stop + "', " + order + ");";
            if (connection != null) {
                Statement statement = connection.createStatement();
                if (checkStoppingRecord(busNumber, stop)) {
                    statement.executeUpdate(query);
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertBusStopRecord(String busStopName, String otherName, String location) {
        try {
            Connection connection = getConnection();
            String query = "INSERT INTO BusStop VALUES ('" + busStopName + "', '" + otherName + "', '" + location + "');";
            if (connection != null) {
                Statement statement = connection.createStatement();
                if (checkBusStopRecord(busStopName, location)) {
                    statement.executeUpdate(query);
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Bus> getBusList() {
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM Bus";
            ObservableList<Bus> busList = FXCollections.observableArrayList();
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int busNo = resultSet.getInt(1);
                    String source = resultSet.getString(2);
                    String destination = resultSet.getString(3);
                    busList.add(new Bus(busNo, source, destination));
                }
                connection.close();
                return busList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteBus(Bus bus) {
        try {
            Connection connection = getConnection();
            String query = "DELETE FROM Bus WHERE bus_number = " + bus.getBusNo() + ";";
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.execute("PRAGMA foreign_keys = ON;");
                statement.executeUpdate(query);
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteBusStop(int oldBusNo) {
        try {
            Connection connection = getConnection();
            String query = "DELETE FROM BusStop\n" +
                    "WHERE name IN (" +
                    "SELECT name FROM BusStop\n" +
                    "INNER JOIN Stopping ON BusStop.name = Stopping.busStop_name\n" +
                    "WHERE Stopping.bus_number = " + oldBusNo +
                    ");";
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteStopping(int oldBusNo) {
        try {
            Connection connection = getConnection();
            String query = "DELETE \n" +
                    "FROM Stopping\n" +
                    "WHERE bus_number = " + oldBusNo + ";";
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Stopping> getStoppingList(int busNo) {
        try {
            Connection connection = getConnection();
            String query = "SELECT Stopping.bus_number, Stopping.order_sequence, BusStop.name, BusStop.other_name, " +
                    "BusStop.location FROM Stopping INNER JOIN BusStop ON Stopping.busStop_name = " +
                    "BusStop.name WHERE bus_number = " + busNo + " ORDER BY order_sequence;";
            ObservableList<Stopping> stoppingList = FXCollections.observableArrayList();
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int order = resultSet.getInt(2);
                    String busStopName = resultSet.getString(3);
                    String busStopOtherName = resultSet.getString(4).substring(1, resultSet.getString(4).length() - 1);
                    String busStopLocation = resultSet.getString(5);
                    stoppingList.add(new Stopping(busNo, new BusStop(busStopName, busStopOtherName, busStopLocation), order));
                }
                connection.close();
                return stoppingList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBusRecord(Bus bus, int oldBusNumber) {
        try {
            Connection connection = getConnection();
            String query = "UPDATE Bus " +
                    "SET bus_number = "+ bus.getBusNo()
                    + ", source = '" + bus.getSource() + "', destination = '" + bus.getDestination() + "' " +
                    "WHERE bus_number = "+ oldBusNumber + ";";
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.execute("PRAGMA foreign_keys = ON;");
                statement.execute(query);

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
