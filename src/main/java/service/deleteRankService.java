package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Connector;
import model.Util;

public class deleteRankService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        
        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();

        String _id = (String) map.get("id");
        String msg = null;
        
        // validation on
        Boolean ID_EXIST;
        int resultType = -1;
        // session validation is needed...? hmmmmm....

        ID_EXIST = _id != null && isIDexist(_id);
        if(ID_EXIST) {
             resultType = deleteUser(_id);
             msg = "result ok";
        }
        else {
            msg = "no such id";
        }

        
        if(resultType == 0) {
            msg = jsonUtil.makeResult(resultType, msg);
        }
        else {
            msg = jsonUtil.makeResult(resultType, msg);
        }

        pw.write(msg);
    }

    private int deleteUser(String id) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;

        String sql = "delete from ranks where id = ?";

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

    private Boolean isIDexist(String id) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "select id from ranks where id = ?";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, id);
            rs = pstm.executeQuery();

            return rs.next();
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            connector.close(conn, pstm, rs);
        }
    }
}