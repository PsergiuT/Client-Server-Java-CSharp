package Controller;

import Domain.Meci;
import Service.ServiceCaserie;
import Service.ServiceUser;
import Validator.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AppController {

    private ServiceCaserie serviceCaserie;
    private ServiceUser serviceUser;

    @FXML
    private TableView<Meci> MeciuriTableView;
    private ObservableList<Meci> meciModel = FXCollections.observableArrayList();


    @FXML
    private TableColumn<Meci, Long> tableColumnMeciId;
    @FXML
    private TableColumn<Meci, String> tableColumnMeciDescriere;
    @FXML
    private TableColumn<Meci, Double> tableColumnMeciPret;
    @FXML
    private TableColumn<Meci, Integer> tableColumnMeciNrLocuri;
    @FXML
    private TableColumn<Meci, Boolean> tableColumnMeciSoldOut;

    @FXML
    private TextField idBiletField;
    @FXML
    private TextField numeClientField;
    @FXML
    private TextField adresaClientField;
    @FXML
    private TextField numarLocuriField;
    @FXML
    private Label errorMessage;

    @FXML
    private Button addBiletButton;
    @FXML
    private Button modificaButton;
    @FXML
    private Button cautaButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button logoutButton;

    public void setService(ServiceUser serviceUser, ServiceCaserie serviceCaserie) {
        this.serviceCaserie = serviceCaserie;
        this.serviceUser = serviceUser;

        initModel(serviceCaserie.findAll());
    }

    @FXML
    public void initialize() {
        tableColumnMeciId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnMeciDescriere.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        tableColumnMeciPret.setCellValueFactory(new PropertyValueFactory<>("pret"));
        tableColumnMeciNrLocuri.setCellValueFactory(new PropertyValueFactory<>("nr_locuri"));
        tableColumnMeciSoldOut.setCellValueFactory(new PropertyValueFactory<>("sold_out"));

        tableColumnMeciSoldOut.setCellFactory(column -> new TableCell<Meci, Boolean>() {
            @Override
            protected void updateItem(Boolean isSoldOut, boolean empty) {
            super.updateItem(isSoldOut, empty);

            if (empty || isSoldOut == null) {
                setText(null);
            } else{
                if(isSoldOut){
                    setText("SOLD OUT");
                    setTextFill(Color.RED);
                } else{
                    setText("");
                }
            }
            }

        });

        MeciuriTableView.setItems(meciModel);
    }


    private void initModel(List<Meci> meciuri){
        try {
            meciModel.setAll(meciuri);
        }
        catch(Exception e){
            errorMessage.setText(e.getMessage());
        }
    }


    @FXML
    public void onAddBiletLogin(ActionEvent event) {
        try{
            Meci m = MeciuriTableView.getSelectionModel().getSelectedItem();
            serviceCaserie.vanzareBilet(m, numeClientField.getText(), adresaClientField.getText(), numarLocuriField.getText());
            initModel(serviceCaserie.findAll());
        }catch(ValidationException ve){
            errorMessage.setText(ve.getMessage());
        }
    }

    @FXML
    public void onModifica(ActionEvent event) {
        try {
            serviceCaserie.modificaLocuri(idBiletField.getText(), numarLocuriField.getText());
            initModel(serviceCaserie.findAll());
        }catch (ValidationException ve){
            errorMessage.setText(ve.getMessage());
        }
    }

    @FXML
    public void onCauta(ActionEvent event) {
        try{
            List<Meci> afterSearch = serviceCaserie.cautaMeciuri(adresaClientField.getText(), numeClientField.getText());
            initModel(afterSearch);
        }catch (ValidationException ve){
            errorMessage.setText(ve.getMessage());
        }

    }

    @FXML
    public void onReset(ActionEvent event) {
        initModel(serviceCaserie.findAll());
    }

    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setTitle("Login");
        stage.setScene(scene);

        LoginController loginController = loader.getController();
        loginController.setService(serviceUser, serviceCaserie);

        stage.show();
    }
}
