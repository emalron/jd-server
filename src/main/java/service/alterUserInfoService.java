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

import model.Connector;
import model.Util;

public class alterUserInfoService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        
        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();

        String _id = (String) map.get("id");
        String _name = (String) map.get("name");
        String _lang = (String) map.get("lang");

        String msg = null;
        
        // validation on
        Boolean ID_EXIST;
        int resultType = -1;
        // session validation is needed...? hmmmmm....

        ID_EXIST = _id != null && isIDexist(_id);
        if(ID_EXIST) {
             resultType = updateUser(_name, _lang, _id);
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

    private int updateUser(String name, String lang, String id) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;

        String sql = "update users set "; // name=?, lang=? where id = ?";
        String seper = "";
        ArrayList<String> params = new ArrayList<String>();

        params.add("");

        if(name != null && name != "") {
            sql += seper;
            sql += "name=" + "?";
            seper = ", ";
            params.add(name);
        }
        if(lang != null && lang != "") {
            sql += seper;
            sql += "lang=" + "?";
            seper = ", ";
            params.add(lang);
        }
        if(id == "") {
            return -1;
        }
        seper = " ";
        sql += seper;
        sql += "where id=" + "?";
        params.add(id);

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

    private Boolean isIDexist(String id) {
        Connector connector = Connector.getInstance();
        Connection conn = connector.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "select name from users where id = ?";

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