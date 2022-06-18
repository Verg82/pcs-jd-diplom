import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class SocketServer extends SocketHandle {
    private int port;
    private BooleanSearchEngine searchEngine;

    public SocketServer(int port) {
        this.port = port;
    }

    public SocketServer setSearchEngine(BooleanSearchEngine searchEngine) {
        this.searchEngine = searchEngine;
        return this;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader bufRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter sockOut = new PrintWriter(socket.getOutputStream(), true)) {
                    sockReadHandler(sockOut, bufRead);
                }
            }
        } catch (IOException ex) {
            System.out.println("Не могу стартовать сервер");
            ex.printStackTrace();
        }
    }

    @Override
    public void sockReadHandler(PrintWriter sockOut, BufferedReader sockRead) {
        String request = read(sockRead);
        Data data = new GsonBuilder().create().fromJson(request, Data.class);
        List<PageEntry> searchResult = searchEngine.search(data.word);
        String json = null;
        if (searchResult != null) {
            json = new JsonParse().listObjectToJson(searchResult.stream().map(element -> (Object) element).collect(Collectors.toList()));
        }
        send(sockOut, json);
    }
}
