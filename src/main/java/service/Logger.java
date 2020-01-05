package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

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
        String json = "{\"text\": \"" + test + "\"}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(this.slackHook))
            .POST(BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();

        try {
            client.sendAsync(request, BodyHandlers.ofString())
            .thenAccept(response -> {
                System.out.println("Slack log: " + response.body());
            });
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}