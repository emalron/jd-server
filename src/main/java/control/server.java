package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.UserDAO;

public class server extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);

        String cmd = req.getParameter("cmd");

        if(cmd.equals("test")) {
            UserDAO uDao = new UserDAO();

            PrintWriter pw = resp.getWriter();
            ArrayList<User> users = new ArrayList<User>();

            users = uDao.test();

            for(User u : users) {
                String out = u.getId() + " "  + u.getName() + ", " + u.getAge();
                pw.println(out);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }
}