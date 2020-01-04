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

public class whereIamService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();

        Util util = Util.getInstance();
        Map<String, Object> map = util.getJson();
        String id = (String) map.get("id");
        int rows_number = Integer.parseInt((String) map.get("rows_number"));

        String jsonString = null;
        ArrayList<Rank> ranks = null;

        Boolean isIDexist = id != null && id != "";
        if(isIDexist) {
            ranks = search(id);
        }

        int myRank = ranks.get(0).getRank();
        int start = myRank - rows_number/2 < 0 ? 0 : myRank - rows_number/2;

        ranks = showAll(start, rows_number);

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

    public ArrayList<Rank> search(String id) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "select (";
        sql += "select count(*) from view_ranking T2 ";
        sql += "where T2.score > T1.score) rank, name, score, replay_data, time ";
        sql += "from view_ranking as T1 ";
        sql += "where id=? order by rank ";

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

    public ArrayList<Rank> showAll(int from, int range) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        // String sql = "select * from view_ranking order by score desc limit " + from + "," + range;
        // 

        String sql = "select (";
        sql += "select count(*) from view_ranking T2 ";
        sql += "where T2.score >= T1.score) rank, name, score, replay_data, time ";
        sql += "from view_ranking as T1 ";
        sql += "order by score desc limit " + from + "," + range;
        System.out.println("query: " + sql);

        ArrayList<Rank> ranks = new ArrayList<Rank>();

        try {
            pstm = conn.prepareStatement(sql);
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
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connector.close(conn, pstm, rs);
        }

        return null;
    }
}