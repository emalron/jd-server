package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {
    Connector connector;
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    public UserDAO() {
        connector = Connector.getInstance();
        conn = null;
        pstm = null;
        rs = null;
    }

    public String getName(String id) {
        conn = connector.getConnection();

        String sql = "select * from users where id = ?";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, id);
            rs = pstm.executeQuery();

            if(rs.next()) {
                return rs.getString(2);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            connector.close(conn, pstm, rs);
        }

        return null;
    }

    public User getUserInfo(String id) {
        conn = connector.getConnection();

        String sql = "select * from users where id = ?";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, id);
            rs = pstm.executeQuery();

            if(rs.next()) {
                User user_ = new User();
                
                user_.setId(rs.getString(1));
                user_.setName(rs.getString(2));
                user_.setLang(rs.getString(3));

                return user_;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            connector.close(conn, pstm, rs);
        }

        return null;
    }

    public void addUser(String id, String name, String lang) {
        conn = connector.getConnection();
        pstm = null;

        String sql = "insert into users(id, name, lang) values(?, ?, ?)";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, lang);

            pstm.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            connector.close(conn, pstm);
        }
    }
    
    public int deleteUser(String id) {
        conn = connector.getConnection();
        pstm = null;

        String sql = "delete from users where id = ?";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, id);

            pstm.executeUpdate();

            return 0;
        }
        catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
        finally {
            connector.close(conn, pstm);
        }
    }

    public Boolean isIDexist(String id) {
        conn = connector.getConnection();
        pstm = null;
        rs = null;

        String sql = "select name from users where id = ?";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, id);
            rs = pstm.executeQuery();

            return rs.next();
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            connector.close(conn, pstm, rs);
        }
    }

    
    public int updateUser(String name, String lang, String id) {
        conn = connector.getConnection();
        pstm = null;

        String sql = "update users set "; // name=?, lang=? where id = ?";
        String seper = "";
        ArrayList<String> params = new ArrayList<String>();

        params.add("");

        if(name != null && name != "") {
            sql += seper;
            sql += "name=" + "?";
            seper = ", ";
            params.add(name);
        }
        if(lang != null && lang != "") {
            sql += seper;
            sql += "lang=" + "?";
            seper = ", ";
            params.add(lang);
        }
        if(id == "") {
            return -1;
        }
        seper = " ";
        sql += seper;
        sql += "where id=" + "?";
        params.add(id);

        try {
            pstm = conn.prepareStatement(sql);

            for(int i=1; i<params.size(); i++) {
                pstm.setString(i, params.get(i));
            }

            pstm.executeUpdate();

            return 0;
        }
        catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
        finally {
            connector.close(conn, pstm);
        }
    }

    public ArrayList<User> showAll() {
        conn = connector.getConnection();
        pstm = null;
        rs = null;

        String sql = "select * from users";
        ArrayList<User> users = new ArrayList<User>();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while(rs.next()) {
                User _user = new User();

                _user.setId(rs.getString(1));
                _user.setName(rs.getString(2));
                _user.setLang(rs.getString(3));

                users.add(_user);
            }

            return users;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connector.close(conn, pstm, rs);
        }

        return null;
    }
}