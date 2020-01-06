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
import model.Util;

public class showAllRanksService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Util util = Util.getInstance();
        RankDAO rankDAO = new RankDAO();

        int resultType = -1;
        String result = null, jsonString = "no ranks";

        ArrayList<Rank> ranks = rankDAO.showAllwithRanking();
        if (ranks != null) {
            resultType = 2;
            ObjectMapper mapper = new ObjectMapper();
            jsonString = mapper.writeValueAsString(ranks);

            ranks = null;
        }
        
        result = util.makeResult(resultType, jsonString);
        pw.write(result);
    }
}