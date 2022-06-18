public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8989;

    public static void main(String[] args) {
        new SocketClient(HOST, PORT).doRequest("бизнес");
    }
}
