package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class loginCheckService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        PrintWriter pw = resp.getWriter();
        String name, msg;
        Boolean isLogin = session.getAttribute("id") != null;

        if(isLogin) {
            name = (String)session.getAttribute("name");
            msg = "Welcome back, " + name;

            resp.setContentType("application/json; charset=utf-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
            pw.write(msg);
        }
    }
    
}