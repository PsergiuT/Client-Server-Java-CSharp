import app.network.utils.AbstractServer;
import app.network.utils.JsonConcurrentServerImpl;
import app.network.utils.ServerException;
import app.persistence.IBiletRepository;
import app.persistence.IMeciRepository;
import app.persistence.IUserRepository;
import app.persistence.jdbc.BiletRepository;
import app.persistence.jdbc.MeciRepository;
import app.persistence.jdbc.UserRepository;
import app.server.AppServicesImpl;
import app.services.IAppServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Props;

import java.util.Properties;

public class StartJsonServer {
    public static int defaultPort = 55555;
    private static Logger logger = LogManager.getLogger(StartJsonServer.class);

    public static void main(String[] args){
        Properties props = Props.getProperties();
        IUserRepository userRepo = new UserRepository(props);
        IBiletRepository biletRepo = new BiletRepository(props);
        IMeciRepository meciRepo = new MeciRepository(props);

        IAppServices services = new AppServicesImpl(userRepo, biletRepo, meciRepo);

        int appServerPort = defaultPort;
        try{
            appServerPort = Integer.parseInt(props.getProperty("app.server.port"));
        }
        catch(NumberFormatException e){
            logger.error(e);
            System.err.println("Error parsing port number: " + e);
        }
        logger.debug("Starting server on port: "+appServerPort);

        //TODO: create and start server
        logger.info("Starting server ...");
        try{
            AbstractServer server = new JsonConcurrentServerImpl(appServerPort, services);
            server.start();
        } catch (ServerException e) {
            logger.error("Error starting the server" + e.getMessage());
        }
    }
}

