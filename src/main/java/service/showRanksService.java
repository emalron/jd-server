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

public class showRanksService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Util util = Util.getInstance();
        Map<String, Object> map = util.getJson();
        RankDAO rankDAO = new RankDAO();
        
        int resultType = -1;
        String result = null, msg = "fail";
        ArrayList<Rank> ranks = null;

        // String id = jwt.findID(req.getCookies());
        String id = (String) map.get("id");
        String mode = (String) map.get("mode");
        if(mode == null) {
            mode = "score";
        }

        Boolean id_exist_check = id != null && !id.isEmpty();
        if(id_exist_check) {
            ranks = rankDAO.search(id, mode);
        }
        else {
            ranks = rankDAO.showAll(mode);
        }

        if(ranks != null) {
            resultType = 2;
            msg = "ok";
        }
        else {
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