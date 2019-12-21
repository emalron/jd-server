package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Rank;
import model.RankDAO;

public class rankSearchService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        PrintWriter pw = resp.getWriter();

        RankDAO rDao = new RankDAO();

        ArrayList<Rank> ranks = rDao.search(name);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(ranks);

        if(ranks == null) {
            pw.print("no return from uDao.test call");
        }
        else {
            resp.setContentType("application/x-json; charset=utf-8");
            pw.print(jsonString);
        }
    }
}