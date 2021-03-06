package model;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RankDAO {
    Connector connector;
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    private static final HashMap<String, String> sqlmap;
    static {
        sqlmap = RankDAO.getSQLMap();
    }


    public RankDAO() {
        connector = Connector.getInstance();
        conn = null;
        pstm = null;
        rs = null;
    }

    public ArrayList<Rank> searchAllof(String id) {
        conn = connector.getConnection();
        pstm = null;
        rs = null;

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

    public ArrayList<Rank> searchRange(int from, int range) {
        conn = connector.getConnection();
        pstm = null;
        rs = null;

        String sql = sqlmap.get("searchRange");
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

    public ArrayList<Rank> search(String id, String mode) {
        conn = connector.getConnection();
        pstm = null;
        rs = null;

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
        conn = connector.getConnection();
        pstm = null;
        rs = null;

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

    public ArrayList<Rank> showAllwithRanking(int count) {
        conn = connector.getConnection();
        pstm = null;
        rs = null;

        String sql = sqlmap.get("showAllwithRanking");
        
        WeakReference<ArrayList<Rank>> wr = new WeakReference<ArrayList<Rank>>( new ArrayList<Rank>() );
        ArrayList<Rank> ranks = wr.get();
        
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
        catch (SQLException e) {
            if(count  < 3) {
                System.out.println("[!] catch exception and try again " + count);
                showAllwithRanking(++count);
            }
            else {
                e.printStackTrace();
            }
        }
        finally {
            connector.close(conn, pstm, rs);
            ranks = null;
        }

        return null;
    }

    public int addRank(int score, String replay, String id) {
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;

        String sql = "insert into ranks(score, replay_data, users_id, time) values (?, ?, ?, now())";

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

    public int deleteRanks(String id) {
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;

        String sql = "delete from ranks where users_id = ?";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, id);

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

    static HashMap<String, String> getSQLMap() {
        HashMap<String, String> map = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append("select rank, name, score, replay_data, time from (");
        sb.append("select name, score, replay_data, time, ");
        sb.append("case when @prev = score then @vRank when @prev := score then @vRank := @vRank+1 end as rank ");
        sb.append("from view_ranking as p, (select @vRank:=0, @prev := null) as r order by score desc ");
        sb.append(") as CNT");
        map.put("showAllwithRanking", sb.toString());

        sb.setLength(0);
        sb.append("select (");
        sb.append("select count(*) from view_ranking T2 ");
        sb.append("where T2.score >= T1.score) rank, name, score, replay_data, time ");
        sb.append("from view_ranking as T1 "); 
        sb.append("order by score desc limit ?, ?");
        map.put("searchRange", sb.toString());

        return map;
    }
}
