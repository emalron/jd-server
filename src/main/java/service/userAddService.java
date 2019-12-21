package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserDAO;

public class userAddService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO uDao = new UserDAO();

        String _name = req.getParameter("name");
        int _age = Integer.parseInt(req.getParameter("age"));

        uDao.addUser(_name, _age);
    }
}