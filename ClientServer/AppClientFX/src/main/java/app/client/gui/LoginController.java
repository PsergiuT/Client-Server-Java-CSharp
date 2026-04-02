package app.client.gui;

import app.model.implementation.Users;
import app.services.AppException;
import app.services.IAppServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label errorMessage;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private IAppServices services;
    private AppController appController;
    private Users user;
    private Parent root;

    @FXML
    private void initialize(){
        errorMessage.setText("");
    }

    public void setService(IAppServices services){
        this.services = services;
    }

    public void setAppController(AppController appCtrl){
        appController = appCtrl;
    }

    @FXML
    public void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        user = new Users(username, password);

        try{
            services.login(user, appController);
            changeView(event);
        }
        catch(AppException | IOException ve){
            errorMessage.setText(ve.getMessage());
        }
    }

    private void changeView(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("App");
        stage.setScene(new Scene(root));

        appController.setUser(user);

        stage.show();
    }

    public void setParent(Parent root){
        this.root = root;
    }
}
