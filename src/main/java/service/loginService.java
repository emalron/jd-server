package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Connector;

public class loginService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String _id = req.getParameter("id");
        HttpSession session = req.getSession();
        Boolean isFirst = session.getAttribute("id") == null;

        String _name = req.getParameter("name");;
        PrintWriter pw = resp.getWriter();

        if(isFirst) {
            if(!isRegistered(_id)) {
                addUser(_id, _name);
            }

            session.setAttribute("id", _id);
            session.setAttribute("name", _name);
        }

        _name = session.getAttribute("name").toString();

        String hello = "Welcome, " + _name;
        pw.write(hello);
    }

    private Boolean isRegistered(String id) {
        Connection conn = Connector.getInstance().getConnection();
        PreparedStatement pstm;
        ResultSet rs;

        String sql = "select * from users where id = ?";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, id);
            rs = pstm.executeQuery();

            if(rs.next()) {
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void addUser(String id, String name) {
        Connection conn = Connector.getInstance().getConnection();
        PreparedStatement pstm;

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
    }
    
}