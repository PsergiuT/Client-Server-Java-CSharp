package Controller;

import Service.ServiceCaserie;
import Service.ServiceUser;
import Validator.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label errorMessage;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private ServiceUser serviceUser;
    private ServiceCaserie serviceCaserie;

    @FXML
    private void initialize(){
        errorMessage.setText("");
    }

    public void setService(ServiceUser serviceUser, ServiceCaserie serviceCaserie){
        this.serviceUser = serviceUser;
        this.serviceCaserie = serviceCaserie;
    }

    @FXML
    public void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try{
            serviceUser.checkCreditentials(username, password);
            changeView(event);
        }
        catch(ValidationException | IOException ve){
            errorMessage.setText(ve.getMessage());
        }
    }

    private void changeView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/app.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setTitle("App");
        stage.setScene(scene);

        AppController controller = loader.getController();
        controller.setService(serviceUser, serviceCaserie);

        stage.show();
    }
}
