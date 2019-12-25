package model;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Connector {
    private static Connection conn;

    public static Connection getConnection() {
        if(conn == null) {
            try {
                String path = Connector.class.getResource("").getPath() + "conf/db.properties";
                System.out.println(path);
                File file = new File(path);
                FileInputStream fis = new FileInputStream(file);
            
                Properties props = new Properties();
                props.load(fis);
            
                fis.close();
            
                String driver = props.getProperty("driver");
                String url = props.getProperty("url");
                String username = props.getProperty("username");
                String password = props.getProperty("password");
            
                Class.forName(driver);
            
                conn = DriverManager.getConnection(url, username, password);
                
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}

