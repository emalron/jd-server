package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RankDAO;
import model.Util;

public class rankAddService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();
        RankDAO rankDAO = new RankDAO();
        JWT jwt = new JWT();

        String token = (String) map.get("jwt");
        String _id = jwt.verify(token);
        int _score = Integer.parseInt((String) map.get("score"));
        String _replay = (String) map.get("replay_data");

        int resultType = -1;
        String msg = null, resultMsg = "fail";

        // validation on
        Boolean id_exist_check = _id != null;
        Boolean negative_score_check = _score >= 0;
        if(id_exist_check && negative_score_check) {
             resultType = rankDAO.addRank(_score, _replay, _id);
             resultMsg = "ok";
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("result", resultType);
        result.put("message", resultMsg);
        
        msg = jsonUtil.makeResult(result);
        pw.write(msg);
    }
}