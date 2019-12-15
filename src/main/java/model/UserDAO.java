package model;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

public class UserDAO {
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    public UserDAO() {
        try {
            String path = this.getClass().getResource("").getPath() + "conf/db.properties";
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

    public ArrayList<User> test() {
        String sql = "select * from test";
        ArrayList<User> users = new ArrayList<User>();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while(rs.next()) {
                User _user = new User();

                _user.setName(rs.getString(1));
                _user.setAge(rs.getInt(2));
                _user.setId(rs.getInt(3));

                users.add(_user);
            }

            return users;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<User> search(String id) {
        String sql = "select * from test where name=?";
        ArrayList<User> users = new ArrayList<User>();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, id);
            rs = pstm.executeQuery();

            while(rs.next()) {
                User _user = new User();

                _user.setName(rs.getString(1));
                _user.setAge(rs.getInt(2));
                _user.setId(rs.getInt(3));

                users.add(_user);
            }
            return users;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}