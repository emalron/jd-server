import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Connector;
import model.User;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        Connector connector = Connector.getInstance();

        try {
            conn = connector.getConnection();
            String sql = "select * from users";
            
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while(rs.next()) {
                User user = new User();

                user.setName(rs.getString(2));
                System.out.println(user.getName());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            connector.close(conn, pstm, rs);
        }
    }
}