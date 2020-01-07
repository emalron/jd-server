package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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

        String _token = null, _id = null, _name = null, _lang =null, msg = null;
        int resultType = -1;

        _token = (String) map.get("jwt");
        _id = jwt.verify(_token);
        _name = (String) map.get("name");
        _lang = (String) map.get("lang");

        // validation on
        Boolean id_exist_check = _id != null;
        if(id_exist_check) {
             resultType = userDAO.updateUser(_name, _lang, _id);
             msg = "ok";
        }
        else {
            msg = "fail";
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("result", resultType);
        result.put("message", msg);

        msg = jsonUtil.makeResult(result);
        pw.write(msg);
    }

}