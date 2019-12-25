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
        String sql = "insert into ranking(name, score, replay_data) values (?, ?, ?)";

        String _name = req.getParameter("name");
        int _score = Integer.parseInt(req.getParameter("score"));
        String _replay = req.getParameter("replay_data");

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, _name);
            pstm.setInt(2, _score);
            pstm.setString(3, _replay);

            pstm.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}