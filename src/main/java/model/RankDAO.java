package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RankDAO {
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    public RankDAO() {
        conn = Connector.getConnection();
    }

    public ArrayList<Rank> showAll() {
        String sql = "select * from ranking order by score desc";
        ArrayList<Rank> ranks = new ArrayList<Rank>();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while(rs.next()) {
                Rank _rank = new Rank();

                _rank.setName(rs.getString(2));
                _rank.setScore(rs.getInt(3));

                ranks.add(_rank);
            }

            return ranks;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Rank> search(String name) {
        String sql = "select * from ranking where name=?";
        ArrayList<Rank> ranks = new ArrayList<Rank>();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, name);
            rs = pstm.executeQuery();

            while(rs.next()) {
                Rank _rank = new Rank();

                _rank.setName(rs.getString(2));
                _rank.setScore(rs.getInt(3));

                ranks.add(_rank);
            }
            return ranks;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addRank(String name, int score, int seed, String inputs) {
        String sql = "insert into ranking(name, score, seed, inputs) values (?, ?, ?, ?)";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, name);
            pstm.setInt(2, score);
            pstm.setInt(3, seed);
            pstm.setString(4, inputs);

            pstm.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}