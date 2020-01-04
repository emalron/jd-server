package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Util;

public class logoutService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        PrintWriter pw = resp.getWriter();

        if(session == null) {
            // T_D
        }
        else {
            session.invalidate();
        }

        Util util = Util.getInstance();

        String bye = util.makeResult(0, "bye");
        pw.write(bye);
    }
}