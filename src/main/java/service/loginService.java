package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserDAO;
import model.Util;

public class loginService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        UserDAO userDAO = new UserDAO();
        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();
        JWT jwt = new JWT();

        String _id = null, _name = null, token_value = null, hello = null, msg = null;
        Cookie token = null;

        _id = (String) map.get("id");
        _name =  userDAO.getName(_id);

        Boolean new_id_check = _name == null;
        if(new_id_check) {
            _name = (String) map.get("name");

            Boolean noName_check = _name == null || _name.isEmpty() || _name.isBlank();
            if(noName_check) {
                _name = "unknown";
            }

            userDAO.addUser(_id, _name);
        }

        token_value = jwt.generate(_id);
        token = new Cookie("jwt_token", token_value);
        token.setMaxAge(60*60*24); // 24 hours
        token.setHttpOnly(true);
        token.setDomain("jsdodge.com");
        resp.addCookie(token);

        hello = "Welcome, " + _name;
        msg = jsonUtil.makeResult(0, hello);
        pw.write(msg);
    }
}