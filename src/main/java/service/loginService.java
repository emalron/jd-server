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

public class loginService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        UserDAO userDAO = new UserDAO();
        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();
        JWT jwt = new JWT();
        StringBuilder sb = new StringBuilder();

        String _id = null, _name = null, token_value = null, hello = null, msg = null;

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

        sb.append("Hello, ").append(_name);
        hello = sb.toString();

        token_value = jwt.generate(_id);

        HashMap<String, Object> result = new HashMap<>();
        result.put("result", 0);
        result.put("jwt", token_value);
        result.put("msg", hello);

        msg = jsonUtil.makeResult(result);
        pw.write(msg);
    }
}