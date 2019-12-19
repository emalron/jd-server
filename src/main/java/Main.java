import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import control.Controller;
import model.Service;
import model.User;
import model.UserDAO;

public class Main {
    public static void main(String[] args) {
        UserDAO uDao = new UserDAO();

        uDao.addUser("min3", 13);

        ArrayList<User> users = new ArrayList<User>();

        users = uDao.test();

        for(User u : users) {
            String out = u.getId() + " " + u.getName() + ", " + u.getAge();
            System.out.println(out);
        }

        users = uDao.search("jes");

        for(User u : users) {
            String out = u.getId() + " " + u.getName() + ", " + u.getAge();
            System.out.println(out);
        }

        Controller ctrl = new Controller();

        Properties prop = ctrl.getProperties("class.properties");
        Iterator iter = prop.keySet().iterator();

        Map<String, Service> map = new HashMap<String, Service>();

        while(iter.hasNext()) {
            String name_ = (String) iter.next();
            String className_ = prop.getProperty(name_);

            try {
                Class class_ = Class.forName(className_);
                if(class_ != null) {
                    Service service_ = (Service) class_.getDeclaredConstructor().newInstance();
                    map.put(name_, service_);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}