package control;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import service.Service;;

public class Ignite {
    private static Map<String, Service> serviceMap = null;

    public static Map<String, Service> getMap() {
        if(serviceMap == null) {
            serviceMap = new HashMap<String, Service>();

            Properties props = Ignite.getProperties("class.properties");

            Iterator iter = props.keySet().iterator();
    
            while(iter.hasNext()) {
                String cmd = (String)iter.next();
                String className = props.getProperty(cmd);
    
                try {
                    Class class_ = Class.forName(className);
                    Service service_ = (Service) class_.getDeclaredConstructor().newInstance();
    
                    serviceMap.put(cmd, service_);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return serviceMap;
    }

    public static Properties getProperties(String name) {
        Properties prop = new Properties();

        String path = Ignite.class.getResource("").getPath();
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