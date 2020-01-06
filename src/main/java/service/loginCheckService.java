package service;

import java.io.IOException;
import java.io.PrintWriter;

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
        JWT jwt = new JWT();

        String id = null, name = null, msg = null, result = null;

        //find id from token cookie
        id = jwt.findID(req.getCookies());

        Boolean isLogin = id != null;
        if(isLogin) {
            name = userDAO.getName(id);
            msg = "Welcome back, " + name;
            
            result = util.makeResult(0, msg);
        }
        else {
            msg = "no login";
            result = util.makeResult(-1, msg);
        }

        pw.write(result);
    }
    
    
}