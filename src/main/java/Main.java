import model.UserDAO;
import model.User;
import java.util.ArrayList;

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
    }
}