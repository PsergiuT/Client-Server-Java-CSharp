import Repo.RepoBilet;
import Repo.RepoMeci;
import Repo.RepoUsers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        Properties props = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("bd.config")) {
            if (input == null) {
                logger.error("Sorry, unable to find bd.config in resources");
                return;
            }
            props.load(input);
        } catch (IOException ex) {
            logger.error("Error loading bd.config {}", String.valueOf(ex));
            ex.printStackTrace();
        }

        RepoUsers repoUsers = new RepoUsers(props);
        RepoMeci repoMeci = new RepoMeci(props);
        RepoBilet repoBilet = new RepoBilet(props);
    }
}
