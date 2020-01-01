import com.mysql.cj.util.Util;

import model.Connector;
import service.Logger;

public class Main {
    public static void main(String[] args) {
        Connector c = Connector.getInstance();

        Util util = new Util();

        Logger log = new Logger(c.getSlack());

        log.test("{\"text\": \"test\"}");
        
    }
}