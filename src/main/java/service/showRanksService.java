package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Connector;
import model.Rank;
import model.Util;

public class showRanksService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();

        Util util = Util.getInstance();
        Map<String, Object> map = util.getJson();
        String id = (String) map.get("id");
        String mode = (String) map.get("mode");

        String jsonString = null;
        ArrayList<Rank> ranks = null;

        Boolean isIDexist = id != null && id != "";
        if(isIDexist) {
            ranks = search(id, mode);
        }
        else {
            ranks = showAll(mode);
        }
        

        ObjectMapper mapper = new ObjectMapper();
        jsonString = mapper.writeValueAsString(ranks);

        int resultType = 2;
        String result = null;

        if (ranks == null) {
            resultType = -1;
            jsonString = "no ranks";
        }

        result = util.makeResult(resultType, jsonString);

        pw.write(result);
    }

    public ArrayList<Rank> search(String id, String mode) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "select (";
        sql += "select count(*) from view_ranking T2 ";
        sql += "where T2.score >= T1.score) rank, name, score, replay_data, time ";
        sql += "from view_ranking as T1 ";
        sql += "where id=? order by " + mode;

        ArrayList<Rank> ranks = new ArrayList<Rank>();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, id);
            rs = pstm.executeQuery();

            while(rs.next()) {
                Rank _rank = new Rank();

                _rank.setRank(rs.getInt(1));
                _rank.setName(rs.getString(2));
                _rank.setScore(rs.getInt(3));
                _rank.setReplay_data(rs.getString(4));
                _rank.setTime(rs.getString(5));

                ranks.add(_rank);
            }
            return ranks;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            connector.close(conn, pstm, rs);
        }

        return null;
    }

    public ArrayList<Rank> showAll(String mode) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sorting = null;

        switch(mode) {
            case "score":
                sorting = "score";
                break;
            case "date":
                sorting = "time";
                break;
            default:
                sorting = "unknown";
                break;
        }

        if(sorting == "unknown") {
            return null;
        }

        String sql = "select * from view_ranking order by " + mode + " desc";

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