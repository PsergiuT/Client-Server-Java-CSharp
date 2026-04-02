package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Props {
    private static final Logger logger = LogManager.getLogger();

    public static Properties getProperties(){
        Properties props = new Properties();
        try (InputStream input = Props.class.getClassLoader().getResourceAsStream("/server.properties")) {
            if (input == null) {
                logger.error("Sorry, unable to find server.properties in resources");
                return null;
            }
            props.load(input);
        } catch (IOException ex) {
            logger.error("Error loading server.properties {}", String.valueOf(ex));
            ex.printStackTrace();
        }

        return props;
    }
}
