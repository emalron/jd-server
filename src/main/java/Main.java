import java.util.ArrayList;

import model.User;
import model.Util;

public class Main {
    public static void main(String[] args) {
        User _user = new User();
        _user.setId("id");
        _user.setName("_name");
        _user.setLang("ko");

        Util jsonUtil = Util.getInstance();

        String hello = jsonUtil.makeResult(_user);
        System.out.println(hello);
        String test = '\\' + hello + '\\';
        System.out.println(test);
    }
}