package system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import system.models.JdbcSQLiteConnection;
import system.models.Employee;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLoginController implements Initializable{

    @FXML
    private Label forgotpassButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    private JdbcSQLiteConnection sqLiteConnection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sqLiteConnection = new JdbcSQLiteConnection();
    }

    @FXML
    public void onLoginButtonHandler(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (sqLiteConnection.checkUsernamePassword(username, password)) {
            errorLabel.setVisible(false);
            String permissionChar = username.substring(0, 2);
            if (permissionChar.equals("bm")) {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../busmanagermain.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Bus Management");
                Employee employee = sqLiteConnection.getEmployeeRecord(username, password);
                BusManagerMainController controller =
                        loader.<BusManagerMainController>getController();
                controller.setUpContent(employee);
                stage.show();
            }
        }
        else {
            errorLabel.setVisible(true);
        }

    }


}
