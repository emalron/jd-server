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

public class deleteUserService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();
        
        PrintWriter pw = resp.getWriter();
        RankDAO rankDAO = new RankDAO();
        UserDAO userDAO = new UserDAO();

        String _id = null, msg = "fail";
        int resultType = -1, status = -1;

        // validation on
        _id = (String) map.get("id");

        Boolean id_exist_check = _id != null;
        if(id_exist_check) {
            // remove ranks records first
            status = rankDAO.deleteRanks(_id);
            
            if(status  == 0) {
                resultType = userDAO.deleteUser(_id);
                msg = "ok";
            }
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