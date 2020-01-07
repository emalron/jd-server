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

    private Logger(String url) {
        this.slackHook = url;
    };

    public static Logger getInstance(String url) {
        if(INSTANCE == null) {
            return INSTANCE = new Logger(url);
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
}