package service;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Logger {
    public Logger() {};

    public void test(String test) {
        try {
            OkHttpClient client2 = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            String json = "{\"text\": \"" + test + "\"}";
            System.out.println("in log, " + json);
            RequestBody body = RequestBody.create(mediaType, json);
                        Request request2 = new Request.Builder()
                        .url("https://hooks.slack.com/services/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")
                                .post(body)
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cache-Control", "no-cache")
                                .addHeader("Host", "hooks.slack.com")
                                .addHeader("accept-encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("cache-control", "no-cache")
                                .build();
            Response response2 = client2.newCall(request2).execute();
            System.out.println(response2.code());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}