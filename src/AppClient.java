import java.io.IOException;

public class AppClient {
    public static void main(String[] args) {
        try {
            new Client().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
