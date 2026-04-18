package app.persistence.jdbc;

import app.persistence.IUserRepository;
import app.persistence.jdbc.exceptions.RepoException;
import app.persistence.jdbc.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserRepository implements IUserRepository{
    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;
    private Connection conn;

    public UserRepository(Properties props) {
        logger.info("Initializing RepoUsersDB with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        this.conn = dbUtils.getConnection();
    }

    @Override
    public boolean login(String username, String password) throws RepoException {
        logger.traceEntry();
        try(PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?")){
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next())
                    return true;
                return false;
            }
        }
        catch(SQLException sql){
            logger.error(sql);
            System.err.println("Error login: " + sql);
            throw new RepoException(List.of("Error login: " + sql));
        }
    }

    @Override
    public void logout() {

    }
}
