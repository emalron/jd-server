package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class loginService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // check if the user is already registered.
        String _id = req.getParameter("id");

        if(isRegistered(_id)) {

        }
        else {

        }
        // 
    }

    private Boolean isRegistered(String id) {

        return false;
    }

    private void addUser(String id) {

    }
    
}