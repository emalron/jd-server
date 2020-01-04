package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Connector;
import model.User;
import model.Util;

public class showAllUsersService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();

        // TO-DO: Validation
        //

        ArrayList<User> users = showAll();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(users);

        int resultType = 2;
        String result = null;

        if (users == null) {
            resultType = -1;
            jsonString = "no users";
        }

        Util util = Util.getInstance();
        result = util.makeResult(resultType, jsonString);

        pw.write(result);
    }

    public ArrayList<User> showAll() {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

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