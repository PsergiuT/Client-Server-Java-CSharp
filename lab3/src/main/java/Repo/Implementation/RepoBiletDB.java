package Repo.Implementation;

import Domain.Bilet;
import Domain.Meci;
import Repo.IRepoBilet;
import Util.JdbcUtils;
import Validator.RepoException;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.Logger;

public class RepoBiletDB implements IRepoBilet {

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;
    private Connection conn;

    public RepoBiletDB(Properties props) {
        logger.info("Initializing RepoBiletDB with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        this.conn = dbUtils.getConnection();
    }

    @Override
    public Bilet findById(Long id_bilet) {
        logger.traceEntry();
        try(PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bilet WHERE id = ?")){
            pstmt.setLong(1, id_bilet);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Long id = rs.getLong("id");
                    String numeClient = rs.getString("numeClient");
                    String adresaClient = rs.getString("adresaClient");
                    Long id_meci = rs.getLong("id_meci");
                    Integer nr_locuri = rs.getInt("nr_locuri");
                    return new Bilet(id, numeClient, adresaClient, id_meci, nr_locuri);
                }
                return null;
            }
        }
        catch(SQLException sql){
            logger.error(sql);
            System.err.println("Error finding ticket by id: " + sql);
            throw new RepoException(List.of("Error finding ticket by id"));
        }
    }

    @Override
    public void vanzareBilet(Long id_meci, String numeClient, String adresaClient, Integer nr_locuri) throws RepoException {
        // adugam biletul in baza de date
        logger.traceEntry();
        try(PreparedStatement pstmt = conn.prepareStatement("INSERT INTO bilet(numeClient, adresaClient, id_meci, nr_locuri) VALUES (?, ?, ?, ?)")){
            pstmt.setString(1, numeClient);
            pstmt.setString(2, adresaClient);
            pstmt.setLong(3, id_meci);
            pstmt.setInt(4, nr_locuri);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error adding ticket: " + e);
            throw new RepoException(List.of("Error adding ticket"));
        }
    }


    @Override
    public void modifica(Long id_bilet, Integer numarLocuri) {
        logger.traceEntry();
        try(PreparedStatement pstmt = conn.prepareStatement("UPDATE bilet SET nr_locuri = ? WHERE id = ?")){
            pstmt.setInt(1, numarLocuri);
            pstmt.setLong(2, id_bilet);
            pstmt.executeUpdate();
        }
        catch(SQLException sql){
            logger.error(sql);
            System.err.println("Error updating ticket: " + sql);
            throw new RepoException(List.of("Error updating ticket"));
        }
    }
}
