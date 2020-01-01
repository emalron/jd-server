package service;

import java.net.URLEncoder;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Logger {
    private String slackHook;

    public Logger(String url) {
        this.slackHook = url;
    };

    public void test(String test) {
        try {
            OkHttpClient client2 = new OkHttpClient();
            // String text = URLEncoder.encode(test, "utf-8");
            String json = "{\"text\": \"" + test + "\"}";
            System.out.println("in log, " + json);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);
            Request request2 = new Request.Builder()
                .url(this.slackHook)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
            Response response2 = client2.newCall(request2).execute();
            System.out.println(response2.code());
            System.out.println(response2.message());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}