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

public class showAllService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();

        ArrayList<Rank> ranks = showAll();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(ranks);


        resp.setContentType("application/json; charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Headers", "X-Requested-With");

        if (ranks == null) {
            pw.print("no return from uDao.test call");
        } else {
            pw.print(jsonString);
        }
    }

    public ArrayList<Rank> showAll() {
        Connection conn = Connector.getConnection();
        PreparedStatement pstm;
        ResultSet rs;

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

                ranks.add(_rank);
            }

            return ranks;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}