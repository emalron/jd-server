package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.UserDAO;
import model.Util;

public class showAllUsersService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        UserDAO userDAO = new UserDAO();
        Util util = Util.getInstance();

        int resultType = -1;
        String result = null, msg = "fail";

        // is validation needed???

        ArrayList<User> users = userDAO.showAll();

        if(users != null) {
            resultType = 2;
            msg = "ok";
        }
        else {
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

        HashMap<String, Object> output = new HashMap<>();

        output.put("result", resultType);
        output.put("message", msg);
        output.put("data", users);

        result = util.makeResult(output);
        pw.write(result);
    }
}