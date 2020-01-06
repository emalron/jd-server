package service;

import java.io.IOException;
import java.io.PrintWriter;

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
        PrintWriter pw = resp.getWriter();
        RankDAO rankDAO = new RankDAO();
        UserDAO userDAO = new UserDAO();
        JWT jwt = new JWT();

        String _id = null, msg = null;
        int resultType = -1, status = -1;

        // validation on
        _id = jwt.findID(req.getCookies());

        Boolean id_exist_check = _id != null;
        if(id_exist_check) {
            // remove ranks records first
            status = rankDAO.deleteRanks(_id);
            
            if(status  == 0) {
                resultType = userDAO.deleteUser(_id);
                msg = "result ok";
            }
        }
        else {
            msg = "no such id";
        }

        msg = jsonUtil.makeResult(resultType, msg);
        pw.write(msg);
    }

}