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

import model.User;
import model.UserDAO;

@WebServlet("/test")
public class server extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public server() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        PrintWriter pw = resp.getWriter();

        if(cmd.equals("test")) {
            UserDAO uDao = new UserDAO();

            ArrayList<User> users = new ArrayList<User>();
    
            users = uDao.test();

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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        PrintWriter pw = resp.getWriter();

        if(cmd.equals("search")) {
            UserDAO uDao = new UserDAO();

            String id = req.getParameter("id");
    
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
    }
}