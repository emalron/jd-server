package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Rank;
import model.RankDAO;
import model.Util;

public class whereIamService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Util util = Util.getInstance();
        Map<String, Object> map = util.getJson();
        RankDAO rankDAO = new RankDAO();
        JWT jwt = new JWT();

        int resultType = -1, rows_number = 10;
        String msg = "fail", result = null;
        ArrayList<Rank> ranks = null;

        String temp_row_number = (String) map.get("rows_number");
        if(temp_row_number != null && !temp_row_number.isEmpty()) {
            rows_number = Integer.parseInt(temp_row_number);
            if(rows_number <= 0)  rows_number = 1;
        }

        String token = (String) map.get("jwt");
        Boolean token_check = token != null;
        if(token_check) {
            String id = jwt.verify(token);
            ranks = rankDAO.searchAllof(id);

            Boolean ranks_size_check = ranks != null && ranks.size() > 0;
            if(ranks_size_check) {
                int myRank = ranks.get(0).getRank();
                int start = myRank - rows_number/2 < 0 ? 0 : myRank - rows_number/2;
                ranks = rankDAO.searchRange(start, rows_number);

                resultType = 2;
                msg = "ok";
            }
        }
        if(resultType == -1) {
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

        map.clear();
        map.put("result", resultType);
        map.put("message", msg);
        map.put("data", ranks);

        result = util.makeResult(map);
        pw.write(result);
    }
}