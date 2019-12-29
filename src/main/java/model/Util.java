package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
    private static Util instance;
    private String latest;
    private Util() {
        latest = null;
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public Map<String, Object> getJson() {
        return jsonParse(this.latest);
    }

    public Map<String, Object> getJson(HttpServletRequest req) {
        String json = getBody(req, true);

        return jsonParse(json);
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
}