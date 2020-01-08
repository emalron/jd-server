package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Util;

public class logoutService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Util util = Util.getInstance();

        String bye = null;

        HashMap<String, Object> result = new HashMap<>();
        result.put("result", 0);
        result.put("message", "bye");

        bye = util.makeResult(result);
        pw.write(bye);
    }
}