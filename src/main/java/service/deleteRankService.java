package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RankDAO;
import model.UserDAO;
import model.Util;

public class deleteRankService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();
        UserDAO userDAO = new UserDAO();
        RankDAO rankDAO = new RankDAO();

        String _id = (String) map.get("id"), msg = "fail";
        int resultType = -1;
        
        // validation on
        Boolean id_exist_check;
        
        id_exist_check = _id != null && userDAO.isIDexist(_id);
        if(id_exist_check) {
             resultType = rankDAO.deleteRanks(_id);
             msg = "ok";
        }
        else {
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("result", resultType);
        result.put("message", msg);

        msg = jsonUtil.makeResult(result);
        pw.write(msg);
    }
}