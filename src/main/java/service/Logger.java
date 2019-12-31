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
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            String json = "token=xoxp-890604305414-876930293347-891617813879-3cc6a30802f7b6971772fff55c222cab&channel=CS4JLCF44&text=" + test;
            System.out.println("in log, " + json);
            RequestBody body = RequestBody.create(mediaType, json);
                        Request request2 = new Request.Builder()
                        .url("https://slack.com/api/chat.postMessage")
                                .post(body)
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cache-Control", "no-cache")
                                .addHeader("Host", "hooks.slack.com")
                                .addHeader("accept-encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("cache-control", "no-cache")
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