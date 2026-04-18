import Controller.LoginController;
import Repo.Implementation.RepoBiletDB;
import Repo.Implementation.RepoMeciDB;
import Repo.Implementation.RepoUsersDB;
import Service.ServiceCaserie;
import Service.ServiceUser;
import Util.Props;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class App extends Application {
    private static final Logger logger = LogManager.getLogger();
    private RepoUsersDB repoUsers;
    private RepoMeciDB repoMeci;
    private RepoBiletDB repoBilet;

    @Override
    public void init() throws Exception {
        Properties props = Props.getProperties();

        if (props == null) {
            throw new RuntimeException("Could not load properties");
        }

        this.repoUsers = new RepoUsersDB(props);
        this.repoMeci = new RepoMeciDB(props);
        this.repoBilet = new RepoBiletDB(props);
        logger.info("App initialized");
    }

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("Starting app, Setting up services");
        ServiceUser serviceUser = new ServiceUser(this.repoUsers);
        ServiceCaserie serviceCaserie = new ServiceCaserie(this.repoMeci, this.repoBilet);

        logger.info("Setting up login scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Login");
        stage.setScene(scene);

        LoginController controller = loader.getController();
        controller.setService(serviceUser, serviceCaserie);

        stage.show();
    }

    public static void main(String[] args) { launch(args);}
}
