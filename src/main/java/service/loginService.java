package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Connector;
import model.Util;

public class loginService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        HttpSession session = req.getSession();

        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();
        
        String _id = (String) map.get("id");
        String _name =  getName(_id);
        if(_name == null) {
            _name = (String) map.get("name");
            if(_name == null) _name = "unknown";

            addUser(_id, _name);
        }

        session.setAttribute("id", _id);
        session.setAttribute("name", _name);

        _name = session.getAttribute("name").toString();

        String hello = "Welcome, " + _name;
        String msg = jsonUtil.makeResult(0, hello);
        pw.write(msg);
    }

    private String getName(String id) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

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

    private void addUser(String id, String name) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;

        String sql = "insert into users(id, name) values(?, ?)";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, id);
            pstm.setString(2, name);

            pstm.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            connector.close(conn, pstm);
        }
    }
}