package app.client;

import app.client.gui.AppController;
import app.client.gui.LoginController;
import app.client.util.Props;
import app.network.jsonprotocol.AppServicesJsonProxy;
import app.services.IAppServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class StartJsonClientFX extends Application {
    private Stage primaryStage;

    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";

    private static Logger logger = LogManager.getLogger(StartJsonClientFX.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties props = Props.getProperties();
        String serverIP = defaultServer;
        int serverPort = defaultPort;
        try {
            serverIP = props.getProperty("app.server.host");
            serverPort = Integer.parseInt(props.getProperty("app.server.port"));
        }
        catch (NumberFormatException ex) {
            logger.error("Wrong port number " + ex.getMessage());
            logger.debug("Using default port: " + defaultPort);
            logger.debug("Using default server: " + defaultServer);
        }

        IAppServices services = new AppServicesJsonProxy(serverIP, serverPort);
        logger.info("Client started");

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/login.fxml"));
        Parent root=loader.load();
        LoginController loginCtrl = loader.getController();
        loginCtrl.setService(services);


        FXMLLoader appLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/app.fxml"));
        appLoader.load();
        AppController appCtrl = appLoader.getController();
        appCtrl.setService(services);

        primaryStage.setTitle("login");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
