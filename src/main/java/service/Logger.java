package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;

import model.Util;

public class Logger {
    private static Logger INSTANCE;
    private String slackHook;
    private String logmode;

    public Logger(String url, String logmode) {
        this.slackHook = url;
    };

    public static Logger getInstance(String url, String logmode) {
        if(INSTANCE == null) {
            return INSTANCE = new Logger(url, logmode);
        }
        return INSTANCE;
    }

    public void test(String test) {
        Util util = Util.getInstance();
        HashMap<String, Object> output = new HashMap<>();

        output.put("text", test);
        String json = util.makeResult(output);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(this.slackHook))
            .POST(BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();

        try {
            client.sendAsync(request, BodyHandlers.ofString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void makeLog(String log) {
        
    }
}