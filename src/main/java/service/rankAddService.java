package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RankDAO;

public class rankAddService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RankDAO rDao = new RankDAO();

        String _name = req.getParameter("name");
        int _score = Integer.parseInt(req.getParameter("score"));

        rDao.addRank(_name, _score);
    }
}