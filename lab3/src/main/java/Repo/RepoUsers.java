package Repo;

import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class RepoUsers implements InterfaceRepoUsers{
    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public RepoUsers(Properties props) {
        logger.info("Initializing RepoUsers with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        dbUtils.getConnection();
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public void logout() {

    }
}
