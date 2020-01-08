package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String result = null, msg = "fail";

        ArrayList<Rank> ranks = rankDAO.showAllwithRanking(0);
        if (ranks != null) {
            resultType = 2;
            msg = "ok";
        }
        else {
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

        HashMap<String, Object> output = new HashMap<>();
        output.put("result", resultType);
        output.put("message", msg);
        output.put("data", ranks);
        
        result = util.makeResult(output);
        pw.write(result);
    }
}