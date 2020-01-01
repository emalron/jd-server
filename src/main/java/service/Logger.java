package service;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Logger {
    public Logger() {};

    public void test(String test) {
        String slackHook = "https://hooks.slack.com/services/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";
        try {
            OkHttpClient client2 = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            String json = "{\"text\": \"" + test + "\"}";
            System.out.println("in log, " + json);
            RequestBody body = RequestBody.create(mediaType, json);
                        Request request2 = new Request.Builder()
                        .url(slackHook)
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