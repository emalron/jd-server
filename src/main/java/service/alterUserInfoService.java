package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserDAO;
import model.Util;

public class alterUserInfoService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();
        UserDAO userDAO = new UserDAO();
        JWT jwt = new JWT();

        String _id = null, _name = null, _lang =null, msg = null;
        int resultType = -1;

        _id = (String) map.get("id");
        _name = (String) map.get("name");
        _lang = (String) map.get("lang");

        // validation on
        Boolean id_exist_check = _id != null;
        if(id_exist_check) {
             resultType = userDAO.updateUser(_name, _lang, _id);
             msg = "result ok";
        }
        else {
            msg = "no such id";
        }

        msg = jsonUtil.makeResult(resultType, msg);
        pw.write(msg);
    }

}