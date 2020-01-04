package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Util;

public class jsonTestService implements Service {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();

        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson();

        String id = (String)map.get("id");

        String result = jsonUtil.makeResult(0, id);
        pw.write(result);
    }

}