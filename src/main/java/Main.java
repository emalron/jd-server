import model.Pojo;
import model.Util;

public class Main {
    public static void main(String[] args) {
        Pojo pojo = new Pojo("Name", 20);

        Util jsonUtil = Util.getInstance();
        String hello = jsonUtil.makeResult(pojo);

        System.out.println(hello);
        String test = '\\' + hello + '\\';
        System.out.println(test);
    }
}