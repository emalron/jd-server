package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import model.UserDAO;
import model.Util;

public class showAllUsersService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        UserDAO userDAO = new UserDAO();
        Util util = Util.getInstance();
        ObjectMapper mapper = new ObjectMapper();

        int resultType = -1;
        String result = null, jsonString = "no users";

        // is validation needed???

        ArrayList<User> users = userDAO.showAll();

        if(users != null) {
            resultType = 2;
            jsonString = mapper.writeValueAsString(users);
            
            users = null;
        }
        
        result = util.makeResult(resultType, jsonString);
        pw.write(result);
    }
}