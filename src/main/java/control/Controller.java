package control;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Util;
import service.Service;

@WebServlet("/service")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Map<String, Service> modelMap = null;

    @Override
    public void init() throws ServletException {
        Ignite.init();
        modelMap = Ignite.getMap();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        // doHandle(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        doHandle(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandle(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandle(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandle(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandle(req, resp);
    }

    void doHandle(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        // get cmd from req
        String cmd = req.getParameter("cmd");
        System.out.println("in doHandle parameter: " + cmd);

        Util jsonUtil = Util.getInstance();
        Map<String, Object> map = jsonUtil.getJson(req);
        
        cmd = (String)map.get("cmd");
        System.out.println("in doHandle: " + map);
        System.out.println("in doHandle: " + cmd);

        // choose the proper class with cmd from the map
        Service service_ = this.modelMap.get(cmd);

        // call the process method
        service_.process(req, resp);
    }
}