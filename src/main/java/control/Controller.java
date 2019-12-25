package control;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Service;

@WebServlet("/service")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        doHandle(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        doHandle(req, resp);
    }

    void doHandle(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Service> modelMap = Ignite.getMap();

        // get cmd from req
        String cmd = req.getParameter("cmd");

        // choose the proper class with cmd from the map
        Service service_ = modelMap.get(cmd);

        // call the process method
        service_.process(req, resp);
    }


}