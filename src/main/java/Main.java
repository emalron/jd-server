import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class Main {
    public static void main(String[] args) {
        String uri = "https://hooks.slack.com/services/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";

        String msg = "test";
        String json = "{\"text\": \""+ msg + "\"}";

        HttpClient client = HttpClient.newBuilder()
        .version(Version.HTTP_1_1)
        .build();

        HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(json))
        .build();

        try {
            client.sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println);
            Thread.sleep(5000);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        
    }
}