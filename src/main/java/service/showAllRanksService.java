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
import model.Rank;
import model.Util;

public class showAllRanksService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();

        ArrayList<Rank> ranks = showAll();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(ranks);

        int resultType = 2;
        String result = null;

        if (ranks == null) {
            resultType = -1;
            jsonString = "no ranks";
        }

        Util util = Util.getInstance();
        result = util.makeResult(resultType, jsonString);

        pw.write(result);
    }

    public ArrayList<Rank> showAll() {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "select * from view_ranking order by score desc";
        ArrayList<Rank> ranks = new ArrayList<Rank>();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while(rs.next()) {
                Rank _rank = new Rank();

                _rank.setName(rs.getString(2));
                _rank.setScore(rs.getInt(3));
                _rank.setReplay_data(rs.getString(4));
                _rank.setTime(rs.getString(5));

                ranks.add(_rank);
            }

            return ranks;
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