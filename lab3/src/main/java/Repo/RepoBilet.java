package Repo;

import Domain.Meci;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.Logger;

public class RepoBilet implements InterfaceRepoBilet{

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public RepoBilet(Properties props) {
        logger.info("Initializing RepoBilet with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        dbUtils.getConnection();
    }

    @Override
    public void vanzareBilet(Meci m, String numeClient, String adresaClient, Integer nr_locuri) {

    }

    @Override
    public List<Meci> cautare(String adresaClient, String numeClient) {
        return List.of();
    }

    @Override
    public void modifica(Long id_bilet, Integer numarLocuri) {

    }
}
