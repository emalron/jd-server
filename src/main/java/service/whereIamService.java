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
import model.Util;

public class whereIamService implements Service {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Util util = Util.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = util.getJson();
        RankDAO rankDAO = new RankDAO();

        int resultType = -1, rows_number = 10;
        String jsonString = "no ranks", result = null;
        ArrayList<Rank> ranks = null;

        String id = (String) map.get("id");
        String temp_row_number = (String) map.get("rows_number");
        if(temp_row_number != null && !temp_row_number.isEmpty()) {
            rows_number = Integer.parseInt(temp_row_number);

            if(rows_number <= 0)  rows_number = 1;
        }

        Boolean id_exist_check = id != null && id != "";
        if(id_exist_check) {
            ranks = rankDAO.searchAllof(id);

            if(ranks != null) {
                int myRank = ranks.get(0).getRank();
                int start = myRank - rows_number/2 < 0 ? 0 : myRank - rows_number/2;
                ranks = rankDAO.searchRange(start, rows_number);

                resultType = 2;
                jsonString = mapper.writeValueAsString(ranks);
            }
        }

        result = util.makeResult(resultType, jsonString);
        pw.write(result);
    }
}