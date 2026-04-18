package Repo.Implementation;

import Domain.Meci;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Repo.IRepoMeci;
import Util.JdbcUtils;
import Validator.RepoException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class RepoMeciDB implements IRepoMeci {
    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;
    private Connection conn;

    public RepoMeciDB(Properties props) {
        logger.info("Initializing RepoMeciDB with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        this.conn = dbUtils.getConnection();
    }


    @Override
    public List<Meci> findAll() throws RepoException {
        logger.traceEntry();
        try(PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM meci")){
            try(ResultSet rs = pstmt.executeQuery()){
                List<Meci> meciuri = new ArrayList<>();
                while(rs.next()){
                    Long id = rs.getLong("id");
                    String descriere = rs.getString("descriere");
                    Float pret = rs.getFloat("pret");
                    Integer nr_locuri = rs.getInt("nr_locuri");
                    boolean sold_out = rs.getBoolean("sold_out");
                    meciuri.add(new Meci(id, descriere, pret, nr_locuri, sold_out));
                }

                return meciuri;
            }
        }
        catch(SQLException sql){
            logger.error(sql);
            System.err.println("Error finding all games: " + sql);
            throw new RepoException(List.of("Error finding all games"));
        }
    }


    @Override
    public Meci findById(Long id_meci) throws RepoException {
        logger.traceEntry();
        try(PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM meci WHERE id = ?")){
            pstmt.setLong(1, id_meci);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Long id = rs.getLong("id");
                    String descriere = rs.getString("descriere");
                    Float pret = rs.getFloat("pret");
                    Integer nr_locuri = rs.getInt("nr_locuri");
                    boolean sold_out = rs.getBoolean("sold_out");
                    return new Meci(id, descriere, pret, nr_locuri, sold_out);
                }
                return null;
            }
        }
        catch(SQLException sql){
            logger.error(sql);
            System.err.println("Error finding game by id: " + sql);
            throw new RepoException(List.of("Error finding game by id"));
        }
    }


    @Override
    public void vanzareLocuri(Long id_meci, Integer nr_locuri) throws RepoException {
        logger.traceEntry();
        try(PreparedStatement pstmt = conn.prepareStatement("UPDATE meci SET nr_locuri = nr_locuri - ? WHERE id = ?")){
            pstmt.setInt(1, nr_locuri);
            pstmt.setLong(2, id_meci);
            pstmt.executeUpdate();
        }
        catch(SQLException sql){
            logger.error(sql);
            System.err.println("Error updating game: " + sql);
            throw new RepoException(List.of("Error updating number of seats"));
        }

    }

    @Override
    public List<Meci> cautare(String adresaClient, String numeClient) throws RepoException {
        logger.traceEntry();
        String sql = """
            SELECT mc.id, mc.descriere, mc.pret, mc.nr_locuri, mc.sold_out
            FROM meci mc
            INNER JOIN bilet bl ON mc.id = bl.id_meci
            WHERE (bl.adresaClient = ? OR ? = '')
            AND (bl.numeClient = ? OR ? = '')
            """;
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, adresaClient);
            pstmt.setString(2, adresaClient);
            pstmt.setString(3, numeClient);
            pstmt.setString(4, numeClient);
            try(ResultSet rs = pstmt.executeQuery()){
                List<Meci> meciuri = new ArrayList<>();
                while(rs.next()) {
                    Long id = rs.getLong("id");
                    String descriere = rs.getString("descriere");
                    Float pret = rs.getFloat("pret");
                    Integer nr_locuri = rs.getInt("nr_locuri");
                    boolean sold_out = rs.getBoolean("sold_out");
                    meciuri.add(new Meci(id, descriere, pret, nr_locuri, sold_out));
                }

                return meciuri;
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("Error finding client game: " + e);
            throw new RepoException(List.of("Error finding client game"));
        }
    }


    @Override
    public void setSoldOut(Long id_meci, boolean sold_out) throws RepoException {
        logger.traceEntry();
        try(PreparedStatement pstmt = conn.prepareStatement("UPDATE meci SET sold_out = ? WHERE id = ?")){
            pstmt.setBoolean(1, sold_out);
            pstmt.setLong(2, id_meci);
            pstmt.executeUpdate();
        }
        catch(SQLException sql){
            logger.error(sql);
            System.err.println("Error updating game: " + sql);
            throw new RepoException(List.of("Error updating number of seats"));
        }
    }
}
