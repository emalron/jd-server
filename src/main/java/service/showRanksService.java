package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Rank;
import model.RankDAO;
import model.UserDAO;
import model.Util;

public class showRanksService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Util util = Util.getInstance();
        Map<String, Object> map = util.getJson();
        RankDAO rankDAO = new RankDAO();
        UserDAO userDAO = new UserDAO();
        
        int resultType = -1;
        String result = null, jsonString = "no ranks";
        ArrayList<Rank> ranks = null;

        // String id = jwt.findID(req.getCookies());
        String id = (String) map.get("id");
        String mode = (String) map.get("mode");
        if(mode == null) {
            mode = "score";
        }

        Boolean id_exist_check = id != null && userDAO.isIDexist(id);
        if(id_exist_check) {
            ranks = rankDAO.search(id, mode);
        }
        else {
            ranks = rankDAO.showAll(mode);
        }

        if(ranks != null) {
            ObjectMapper mapper = new ObjectMapper();
            jsonString = mapper.writeValueAsString(ranks);

            resultType = 2;
        }

        result = util.makeResult(resultType, jsonString);
        pw.write(result);
    }
}