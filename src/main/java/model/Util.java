package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import service.Logger;

public class Util {
    private String latest = null;
    private static Util INSTANCE = null;
    Logger log;

    private Util() {
        init();
    }

    public static Util getInstance() {
        if(INSTANCE == null) {
            return INSTANCE = new Util();
        }
        return INSTANCE;
    }

    private void init() {
        Connector c = Connector.getInstance();
        this.log = Logger.getInstance(c.getSlack());
    }

    public Map<String, Object> getJson() {
        if(this.latest == null) {
            System.out.println("this.latest is null in getJson()");
        }
        return jsonParse(this.latest);
    }

    public Map<String, Object> getJson(HttpServletRequest req) {
        String json = getBody(req, true);
        Map<String, Object> res = jsonParse(json);

        makeLog(req, res.toString());
        return res;
    }

    public String makeResult(int type, String message) {
        String msg = null;
        switch(type) {
            case -1:
            case 0:
            case 1:
                // message = plain text
                msg = "{\"result\":"  + type + ", \"message\": \"" + message +"\"}";
                break;
            case 2:
                // message = array
                msg = "{\"result\":"  + type + ", \"message\": "+ message +"}";
                break;
        }

        // msg = "{\"result\":"  + type + ", \"message\": \"" + message +"\"}";
        return msg;
    }

    public String makeResult(HashMap<String, Object> result) {
        String output = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            output = mapper.writeValueAsString(result);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        return output;
    }

    public String makeResult(Object result) {
        String output = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            output = mapper.writeValueAsString(result);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        return output;
    }

    private Map<String, Object> jsonParse(String rawdata) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<String, Object>();
            map = mapper.readValue(rawdata, new TypeReference<Map<String, Object>>() {});
            return map;
        }
        catch(JsonGenerationException e) {
            e.printStackTrace();
        }
        catch(JsonMappingException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getBody(HttpServletRequest req, Boolean isFirst) {
        String body;
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = null;

        if(isFirst == false) {
            System.out.println("getBody false: " + this.latest);
            return this.latest;
        }

        try {
            InputStream inputStream = req.getInputStream();
            if(inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] buffer = new char[128];
                int bytesRead = -1;

                while((bytesRead = bufferedReader.read(buffer)) > 0) {
                    builder.append(buffer, 0, bytesRead);
                }
            }
            else {
                builder.append("");
            }
            
            body = builder.toString();
            this.latest = body;
            System.out.println("getBody true: " + this.latest);
            return body;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private void makeLog(HttpServletRequest req, String body) {
        
        String ip = req.getRemoteAddr();
        String result = ip + " " + body;

        log.test(result);
    }
}