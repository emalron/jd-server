package model;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class Connector {
    private static Connector instance_;
    private Connection conn;
    private String driver, url, username, password, slack;
    private Properties props;

    private Connector() {
        init();
    }

    public static Connector getInstance() {
        if(instance_ == null) {
            return new Connector();
        }

        return instance_;
    }

    public String getSlack() {
        return this.slack;
    }

    private void init() {
        try {
            String path = "/home/emalron/key/db.properties";
            System.out.println(path);
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
        
            PoolConnFactory.registerJDBCDriver(driver);
            ConnectionFactory cFactory = PoolConnFactory.getConnFactory(url, username, password);
            PoolableConnectionFactory poolFactory = new PoolableConnectionFactory(cFactory, null);
            ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<PoolableConnection>(poolFactory, PoolConnFactory.getPoolConfig());
            poolFactory.setPool(connectionPool);
            PoolingDriver dbcpDriver = PoolConnFactory.getDBCDriver();
            dbcpDriver.registerPool("dbcp-2", connectionPool);

            conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:dbcp-2");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if(conn == null) {
                init();
            }
            if(conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:dbcp-2");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return conn;
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

