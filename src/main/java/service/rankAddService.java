package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Connector;
import model.Util;

public class rankAddService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        
        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();

        String _id = (String) map.get("id");
        String _pscore = (String) map.get("score");
        String _replay = (String) map.get("replay_data");
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
        int resultType = addRank(_score, _replay, _id);

        String msg = null;
        if(resultType == 1) {
            msg = jsonUtil.makeResult(resultType, "result ok");
        }
        else {
            msg = jsonUtil.makeResult(resultType, "fail");
        }

        pw.write(msg);
    }

    public int addRank(int score, String replay, String id) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;

        String sql = "insert into ranks(score, replay_data, users_id) values (?, ?, ?)";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, score);
            pstm.setString(2, replay);
            pstm.setString(3, id);

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
}