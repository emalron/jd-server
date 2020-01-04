import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Connector;
import model.Rank;

public class Main {
    public static void main(String[] args) {
        ArrayList<Rank> ranks = showAlls();
    }

    public static ArrayList<Rank> showAlls() {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "select rank, name, score, replay_data, time from (";
            sql += "select name, score, replay_data, time, ";
            sql += "case when @prev = score then @vRank when @prev := score then @vRank := @vRank+1 end as rank ";
            sql += "from view_ranking as p, (select @vRank:=0, @prev := null) as r order by score desc ";
            sql += ") as CNT";

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

                String output = _rank.getRank() + " " + _rank.getName() + " " + _rank.getScore();
                System.out.println(output);
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