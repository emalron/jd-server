package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Service;

@WebServlet("/service")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    Map<String, Service> modelMap = new HashMap<String, Service>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        Properties props = this.getProperties("class.properties");

        Iterator iter = props.keySet().iterator();

        while(iter.hasNext()) {
            String cmd = (String)iter.next();
            String className = props.getProperty(cmd);

            try {
                Class class_ = Class.forName(className);
                Service service_ = (Service) class_.getDeclaredConstructor().newInstance();

                modelMap.put(cmd, service_);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

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
        // get cmd from req
        String cmd = req.getParameter("cmd");

        // choose the proper class with cmd from the map
        Service service_ = modelMap.get(cmd);

        // call the process method
        service_.process(req, resp);
    }

    public Properties getProperties(String name) {
        Properties prop = new Properties();

        String path = this.getClass().getResource("").getPath();
        path += "conf/" + name;

        File file = new File(path);
        try {
            FileInputStream fis = new FileInputStream(file);
            prop.load(fis);

            return prop;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}