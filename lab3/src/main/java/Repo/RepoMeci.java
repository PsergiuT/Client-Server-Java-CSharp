package Repo;

import Domain.Meci;

import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class RepoMeci implements InterfaceRepoMeci{
    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public RepoMeci(Properties props) {
        logger.info("Initializing RepoMeci with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        dbUtils.getConnection();
    }

    @Override
    public List<Meci> findAll() {
        return List.of();
    }

    @Override
    public Meci findById(Long id_meci) {
        return null;
    }

    @Override
    public void vanzare(Integer nr_locuri, Long id_meci) {

    }
}
