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

public class loginCheckService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        UserDAO userDAO = new UserDAO();
        Util util = Util.getInstance();
        Map<String, Object> map = util.getJson();

        JWT jwt = new JWT();

        String id = null, token = null, name = null, result = null, msg = "no login";
        int resultType = -1;

        token = (String) map.get("jwt");
        if(token != null ) id = jwt.verify(token);

        Boolean isLogin = id != null;
        if(isLogin) {
            name = userDAO.getName(id);
            msg = "ok";
            resultType = 0;
        }

        HashMap<String, Object> output = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", name);

        output.put("result", resultType);
        output.put("message", msg);
        output.put("data", data);

        result = util.makeResult(output);
        pw.write(result);

        result = null;
        output = null;
        data = null;
    }
}