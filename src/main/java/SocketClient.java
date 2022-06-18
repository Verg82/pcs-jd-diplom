import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient extends SocketHandle {
    private String host;
    private int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void doRequest(String request) {
        try (Socket socket = new Socket(host, port);
             BufferedReader bufRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter sockOut = new PrintWriter(socket.getOutputStream(), true)) {

            if (socket.isConnected()) {
                send(sockOut, new JsonParse().objectToJson(new Data(request)));
                sockReadHandler(sockOut, bufRead);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sockReadHandler(PrintWriter sockOut, BufferedReader sockRead) {
        String data = read(sockRead);
        if (!data.equals("null")) System.out.println(new JsonParse().jsonPrettyPrint(data));
        else System.out.println("Ничего не найдено");
    }
}
