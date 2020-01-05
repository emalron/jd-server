package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Connector;
import model.Util;

public class loginCheckService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        PrintWriter pw = resp.getWriter();
        JWT jwt = new JWT();

        String id, name, msg, result=null;

        //find token cookie
        Cookie token = null;
        for(Cookie c : cookies) {
            if(c.getName().equals("jwt_token")) {
                token = c;
            }
        }

        Boolean isLogin = token != null && token.getValue() != null;

        Util util = Util.getInstance();
        if(isLogin) {
            System.out.println("[loginCheck] token " +token.getName() + " " + token.getValue());
            id = jwt.verify(token.getValue());
            name = getName(id);
            msg = "Welcome back, " + name;
            
            result = util.makeResult(0, msg);
        }
        else {
            msg = "no login";
            result = util.makeResult(-1, msg);
        }

        pw.write(result);
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
}