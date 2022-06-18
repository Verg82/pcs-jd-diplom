import java.io.File;

public class Main {
    private static final int PORT = 8989;

    public static void main(String[] args) throws Exception {
        System.out.println("Indexing..");
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));

        System.out.println("Start server..");
        new SocketServer(PORT).setSearchEngine(engine).startServer();
    }
}
