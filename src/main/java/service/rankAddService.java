package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Connector;

public class rankAddService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = Connector.getConnection();
        PreparedStatement pstm;
        String sql = "insert into ranking(name, score, seed, ) values (?, ?)";

        String _name = req.getParameter("name");
        int _score = Integer.parseInt(req.getParameter("score"));
        int _seed = Integer.parseInt(req.getParameter("SEED"));
        String _inputs = req.getParameter("inputs");

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, _name);
            pstm.setInt(2, _score);
            pstm.setInt(3, _seed);
            pstm.setString(4, _inputs);

            pstm.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}