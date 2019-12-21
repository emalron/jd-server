package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import model.UserDAO;

public class userSearchService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("name");
        PrintWriter pw = resp.getWriter();

        UserDAO uDao = new UserDAO();

        ArrayList<User> users = uDao.search(id);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(users);

        if(users == null) {
            pw.print("no return from uDao.test call");
        }
        else {
            resp.setContentType("application/x-json; charset=utf-8");
            pw.print(jsonString);
        }
    }
}