package Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.Properties;


public class JdbcUtils {

    private Properties jdbcProps;

    private static final Logger logger = LogManager.getLogger();
    private Connection instance = null;

    public JdbcUtils(Properties props) {
        jdbcProps = props;
    }

    private Connection getNewConnection() {
        logger.traceEntry();

        try {
            Connection conn = DriverManager.getConnection(jdbcProps.getProperty("jdbc.url"));

//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table'");
//
//            while (rs.next()) {
//                logger.info("Table: " + rs.getString("name"));
//            }

            return conn;
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting connection " + e);
        }
        return null;
    }

    public Connection getConnection() {
        logger.traceEntry();
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(instance);
        return instance;
    }
}

