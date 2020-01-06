package service;

import java.io.IOException;
import java.io.PrintWriter;
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

        String _id = (String) map.get("id");
        int _score = Integer.parseInt((String) map.get("score"));
        String _replay = (String) map.get("replay_data");
        
        // validation on
        Boolean id_exist_check, negative_score_check;
        int resultType = -1;
        String msg = null, resultMsg = "fail";

        id_exist_check = _id != null;
        negative_score_check = _score >= 0;
        if(id_exist_check && negative_score_check) {
             resultType = rankDAO.addRank(_score, _replay, _id);
             resultMsg = "result ok";
        }
        
        msg = jsonUtil.makeResult(resultType, resultMsg);
        pw.write(msg);
    }
}