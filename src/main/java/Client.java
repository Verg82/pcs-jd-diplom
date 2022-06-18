public class Client {
    public static void main(String[] args) {
        new SocketClient("localhost", 8989).doRequest("бизнес");
    }
}
