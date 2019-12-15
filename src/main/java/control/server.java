package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.UserDAO;

@WebServlet("/test")
public class server extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public server() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        PrintWriter pw = resp.getWriter();

        pw.write("<html>");
        pw.write("<body>");
        pw.write("cmd<br>");
        pw.write(cmd);

        if(cmd.equals("test")) {
            UserDAO uDao = new UserDAO();

            ArrayList<User> users = new ArrayList<User>();
    
            users = uDao.test();

            if(users == null) {
                pw.write("no return from uDao.test call");
                pw.write("<br>");
            }
    
            for(User u : users) {
                String out = u.getId() + " " + u.getName() + ", " + u.getAge();
                pw.write(out);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }
}