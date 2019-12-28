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
        Connection conn = Connector.getInstance().getConnection();
        PreparedStatement pstm;
        String sql = "insert into ranks(score, replay_data, users_id) values (?, ?, ?)";

        String _id = req.getParameter("id");
        String _pscore = req.getParameter("score");
        String _replay = req.getParameter("replay_data");

        int _score;

        if(_pscore != null) {
            _score = Integer.parseInt(_pscore);            
        }
        else {
            _score = 0;
        }

        // validation on
        Boolean ID_EXIST, SCORE_NEGATIVE;
        // ID_EXIST = _id != null && ;

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, _score);
            pstm.setString(2, _replay);
            pstm.setString(3, _id);

            pstm.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


}