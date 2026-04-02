package app.client.gui;

import app.model.implementation.Meci;
import app.model.implementation.Users;
import app.network.jsonprotocol.AppClientJsonWorker;
import app.services.AppException;
import app.services.IAppObserver;
import app.services.IAppServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements IAppObserver{

    @FXML
    private TableView<Meci> MeciuriTableView;
    private final ObservableList<Meci> meciModel = FXCollections.observableArrayList();


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


    private IAppServices services;
    private Users user;
    private static Logger logger = LogManager.getLogger(AppClientJsonWorker.class);

    public void setService(IAppServices services) {
        this.services = services;
    }

    public void setUser(Users user){
        this.user = user;
    }


    @FXML
    public void initialize() {
        logger.info("INITIALIZE called on instance: " + this.hashCode());
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
        // TODO: find out why initialize() is not called
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
            services.vanzareBilet(m, numeClientField.getText(), adresaClientField.getText(), numarLocuriField.getText());
            initModel(services.findAll());
        }catch(AppException ve){
            errorMessage.setText(ve.getMessage());
        }
    }

    @FXML
    public void onModifica(ActionEvent event) {
        try {
            services.modificaLocuri(idBiletField.getText(), numarLocuriField.getText());
            initModel(services.findAll());
        }catch (AppException ve){
            errorMessage.setText(ve.getMessage());
        }
    }

    @FXML
    public void onCauta(ActionEvent event) {
        try{
            List<Meci> afterSearch = services.cautaMeciuri(adresaClientField.getText(), numeClientField.getText());
            initModel(afterSearch);
        }catch (AppException ve){
            errorMessage.setText(ve.getMessage());
        }

    }

    @FXML
    public void onReset(ActionEvent event) {
        initModel(services.findAll());
    }

    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setTitle("Login");
        stage.setScene(scene);

        LoginController loginController = loader.getController();
        loginController.setService(services);

        stage.show();
    }

    @Override
    public void soldTicket(List<Meci> matches) throws AppException {
        initModel(matches);
    }

    @Override
    public void updateTicket(List<Meci> matches) throws AppException {
        initModel(matches);
    }


}
