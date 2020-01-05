import service.JWT;

public class Main {
    public static void main(String[] args) {
        JWT jwt = new JWT();

        String token = jwt.generate();
        
        System.out.println(token);
    }
}