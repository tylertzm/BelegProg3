import cli.TCPclient;

public class MainClient {
    public static void main(String[] args) {
        new TCPclient("localhost", 12345).run();
    }
}