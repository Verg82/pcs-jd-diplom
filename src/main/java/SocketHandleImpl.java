import java.io.BufferedReader;
import java.io.PrintWriter;

public interface SocketHandleImpl {
    String read(BufferedReader sockRead);
    void send(PrintWriter sockOut, String message);
    void sockReadHandler(PrintWriter sockOut, BufferedReader sockRead);
}
