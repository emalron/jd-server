package service;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

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
        OkHttpClient client2 = new OkHttpClient();
        Request request2 = null;
        RequestBody body = null;
        Response response2 = null;

        try {
            String json = "{\"text\": \"" + test + "\"}";
            System.out.println("in log, " + json);
            MediaType mediaType = MediaType.parse("application/json");
            body = RequestBody.create(mediaType, json);
            request2 = new Request.Builder()
                .url(this.slackHook)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
            response2 = client2.newCall(request2).execute();
            response2.body().close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(response2 != null) {
                try {
                    response2.body().close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}