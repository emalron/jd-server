import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import model.Connector;

public class Main {
    public static void main(String[] args) {
        updateUser(null, "ko", "abc");
    }

    private static int updateUser(String name, String lang, String id) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;

        String sql = "update users set "; // name=?, lang=? where id = ?";
        String seper = "";
        ArrayList<String> params = new ArrayList<String>();

        params.add("");

        if(name != null) {
            sql += seper;
            sql += "name=" + "?";
            seper = ", ";
            params.add(name);
        }
        if(lang != null) {
            sql += seper;
            sql += "lang=" + "?";
            seper = ", ";
            params.add(lang);
        }
        if(id == null) {
            return -1;
        }
        seper = " ";
        sql += seper;
        sql += "where id=" + "?";
        params.add(id);

        String debug = name + " " + lang + " " + id;
        System.out.println(debug);

        for(int i=1; i<params.size(); i++) {
            debug = Integer.toString(i) + " " + params.get(i);
            System.out.println(debug);
        }

        try {
            pstm = conn.prepareStatement(sql);

            for(int i=1; i<params.size(); i++) {
                pstm.setString(i, params.get(i));
            }

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