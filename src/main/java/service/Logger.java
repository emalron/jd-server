package service;

import java.net.URLEncoder;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class Logger {
    public Logger() {};

    public void test(String test) {
        String slackHook = "https://slack.com/api/chat.postMessage?token=xoxp-12751588838-12755505922-878921451395-b5a3f9f609725a4114d4738e10be072a&channel=%23test&text=";
        try {
            OkHttpClient client2 = new OkHttpClient();
            String text = URLEncoder.encode(test, "utf-8");
            String url = slackHook + text + "&pretty=1";
            System.out.println("in log, " + url);
            Request request2 = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
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