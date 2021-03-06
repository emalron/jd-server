package model;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class Connector {
    private String driver, url, username, password, slack, jwt, logmode;
    private Properties props;
    private BasicDataSource datasource;

    private Connector() {
        init();
    }

    private static class connHolder {
        public static final Connector INSTANCE = new Connector();
    }

    public static Connector getInstance() {
        return connHolder.INSTANCE;
    }

    public String getSlack() {
        return this.slack;
    }

    public String getJWT() {
        return this.jwt;
    }

    public String getLogmode() {
        return this.logmode;
    }

    private void init() {
        try {
            String path = "/home/emalron/key/db.properties";
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
        
            props = new Properties();
            props.load(fis);
        
            fis.close();

            driver = props.getProperty("driver");
            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");
            slack = props.getProperty("slack");
            jwt = props.getProperty("jwt");
            logmode = props.getProperty("logmode");

            HashMap<String, Integer> config = new HashMap<>();
            config.put("minIdle", Integer.parseInt(props.getProperty("minIdle")));
            config.put("maxIdle", Integer.parseInt(props.getProperty("maxIdle")));
            config.put("maxTotal", Integer.parseInt(props.getProperty("maxTotal")));

            // datasource = new BasicDataSource();
            // datasource.setDriverClassName(driver);
            // datasource.setUsername(username);
            // datasource.setPassword(password);
            // datasource.setUrl(url);
            // datasource.setMinIdle(config.get("minIdle"));
            // datasource.setMaxIdle(config.get("maxIdle"));
            // datasource.setMaxTotal(config.get("maxTotal"));
            // datasource.setInitialSize(config.get("initialSize"));
        
            PoolConnFactory.registerJDBCDriver(driver);
            ConnectionFactory cFactory = PoolConnFactory.getConnFactory(url, username, password);
            PoolableConnectionFactory poolFactory = new PoolableConnectionFactory(cFactory, null);
            ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<PoolableConnection>(poolFactory, PoolConnFactory.getPoolConfig(config));
            poolFactory.setPool(connectionPool);
            poolFactory.setValidationQuery("SELECT 1");
            PoolingDriver dbcpDriver = PoolConnFactory.getDBCDriver();
            dbcpDriver.registerPool("dbcp-2", connectionPool);

            // Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            // conn = datasource.getConnection();
            conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:dbcp-2");
            // conn = DriverManager.getConnection(url, username, password);
            return conn;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public void close(Connection conn, PreparedStatement pstm, ResultSet rs) {
        try {
            if(rs != null) rs.close();
            if(pstm != null) pstm.close();
            if(conn != null) conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void close(Connection conn, PreparedStatement pstm) {
        try {
            if(pstm != null) pstm.close();
            if(conn != null) conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void close(Connection conn) {
        try {
            if(conn != null) conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

