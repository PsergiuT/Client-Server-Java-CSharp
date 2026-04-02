package app.client;

import app.client.gui.AppController;
import app.client.gui.LoginController;
import app.client.util.Props;
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

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    private static Logger logger = LogManager.getLogger(StartJsonClientFX.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties props = Props.getProperties();
        try {
            String serverIP = props.getProperty("app.server.host", defaultServer);
            int serverPort = Integer.parseInt(props.getProperty("app.server.port"));
        }
        catch (NumberFormatException ex) {
            logger.error("Wrong port number " + ex.getMessage());
            logger.debug("Using default port: " + defaultChatPort);
        }

        // TODO: create the Proxy for IChatServices
        IAppServices services = null;

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/login.fxml"));
        Parent root=loader.load();
        LoginController ctrl = loader.getController();
        ctrl.setService(services);


        FXMLLoader cloader = new FXMLLoader(getClass().getClassLoader().getResource("views/login.fxml"));
        AppController appCtrl = cloader.getController();
        appCtrl.setService(services);

        primaryStage.setTitle("login");
        primaryStage.setScene(new Scene(root, 300, 130));
        primaryStage.show();
    }
}
