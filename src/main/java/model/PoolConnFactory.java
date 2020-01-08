package model;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class PoolConnFactory {
    public static Class driverClass;
    private static PoolingDriver driver;
    private static GenericObjectPoolConfig poolConfig;

    public static void registerJDBCDriver(String driver) {
        try {
            driverClass = Class.forName(driver);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getConnFactory(String url, String user, String password) {
        ConnectionFactory _cFactory = new DriverManagerConnectionFactory(url, user, password);
        return _cFactory;
    }

    public static PoolingDriver getDBCDriver() {
        String DBCPdriver = "org.apache.commons.dbcp2.PoolingDriver";
        try {
            Class.forName(DBCPdriver);
        }
        catch(ClassNotFoundException e) {
            System.err.println("No class found.");
        }

        try {
            driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        return driver;
    }

    public static void registerPool(String poolName, ObjectPool pool) {
        driver.registerPool(poolName, pool);
    }

    public static GenericObjectPoolConfig getPoolConfig() {
        poolConfig = new GenericObjectPoolConfig();

        poolConfig.setTimeBetweenEvictionRunsMillis(1000*5);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setMinIdle(5);
        poolConfig.setMaxIdle(20);
        poolConfig.setMaxTotal(20);

        return poolConfig;
    }
}