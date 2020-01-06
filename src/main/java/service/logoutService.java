package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Util;

public class logoutService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Util util = Util.getInstance();
        JWT jwt = new JWT();

        String bye = null;

        // find jwt cookie
        Cookie token = jwt.findTokenCookie(req.getCookies());

        if(token == null) {
            // T_D
        }
        else {
            token.setMaxAge(0);
            resp.addCookie(token);
        }

        bye = util.makeResult(0, "bye");
        pw.write(bye);
    }
}