package model;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Connector {
    private static Connector instance_;
    private Connection conn;
    private String driver, url, username, password;
    private Properties props;

    public Connector() {
        init();
    }

    public static Connector getInstance() {
        if(instance_ == null) {
            return new Connector();
        }

        return instance_;
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

            Class.forName(driver);
        
            conn = DriverManager.getConnection(url, username, password);
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
                conn = DriverManager.getConnection(url, username, password);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}

