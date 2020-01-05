package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Util;

public class logoutService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        PrintWriter pw = resp.getWriter();

        // find jwt cookie
        Cookie token = null;
        for(Cookie c : cookies) {
            if(c.getName().equals("jwt_token")) {
                token = c;
            }
        }

        if(token == null) {
            // T_D
        }
        else {
            token.setMaxAge(0);
            resp.addCookie(token);
        }

        Util util = Util.getInstance();

        String bye = util.makeResult(0, "bye");
        pw.write(bye);
    }
}