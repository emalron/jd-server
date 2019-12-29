package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Util;

public class loginCheckService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        PrintWriter pw = resp.getWriter();
        String name, msg;
        Boolean isLogin = session != null && session.getAttribute("id") != null;

        Util util = Util.getInstance();
        String result = null;

        if(isLogin) {
            name = (String)session.getAttribute("name");
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