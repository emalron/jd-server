package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.UserDataHandler;

import model.User;
import model.UserDAO;

@WebServlet("/test")
public class server extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public server() {
        super();
    }

    @Override
    public void init(final ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        super.init(config);
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final String cmd = req.getParameter("cmd");
        final PrintWriter pw = resp.getWriter();

        if (cmd.equals("test")) {
            final UserDAO uDao = new UserDAO();

            ArrayList<User> users = new ArrayList<User>();

            users = uDao.test();

            final ObjectMapper mapper = new ObjectMapper();
            final String jsonString = mapper.writeValueAsString(users);

            if (users == null) {
                pw.print("no return from uDao.test call");
            } else {
                resp.setContentType("application/x-json; charset=utf-8");
                pw.print(jsonString);
            }
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        PrintWriter pw = resp.getWriter();
        UserDAO uDao = new UserDAO();

        if (cmd.equals("search")) {
            

            String id = req.getParameter("name");

            ArrayList<User> users = uDao.search(id);

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(users);

            if(users == null) {
                pw.print("no return from uDao.test call");
            }
            else {
                resp.setContentType("application/x-json; charset=utf-8");
                pw.print(jsonString);
            }
        }
        else if (cmd.equals("add")) {
            String _name = req.getParameter("name");
            int _age = Integer.parseInt(req.getParameter("age"));

            uDao.addUser(_name, _age);
        }
    }
}