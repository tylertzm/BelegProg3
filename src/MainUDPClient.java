import udp.UDPclient;

public class MainUDPClient {
    public static void main(String[] args) {
        UDPclient client = new UDPclient();
        client.start();
    }
}