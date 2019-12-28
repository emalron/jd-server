package model;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Connector {
    private static Connection conn;
    private static String driver, url, username, password;

    public static Connection getConnection() {
        
        if(conn == null) {
            try {
                String path = "/home/emalron/key/db.properties";
                System.out.println(path);
                File file = new File(path);
                FileInputStream fis = new FileInputStream(file);
            
                Properties props = new Properties();
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

        try {
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

