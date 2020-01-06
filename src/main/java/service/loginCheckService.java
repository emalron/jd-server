package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.UserDAO;
import model.Util;

public class loginCheckService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        UserDAO userDAO = new UserDAO();
        Util util = Util.getInstance();
        Map<String, Object> map = util.getJson();
        HashMap<String, Object> output = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        JWT jwt = new JWT();

        String id = null, token = null, name = null, result = null, msg = "no login";
        int resultType = -1;

        token = (String) map.get("jwt");
        id = jwt.verify(token);

        Boolean isLogin = id != null;
        if(isLogin) {
            name = userDAO.getName(id);
            msg = "Welcome back, " + name;
            
            resultType = 0;
        }

        
        output.put("result", resultType);
        output.put("message", msg);
        result = util.makeResult(output);

        pw.write(result);

        result = null;
        mapper = null;
        output = null;
    }
    
    
}