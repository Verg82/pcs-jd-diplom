import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class SocketHandle implements SocketHandleImpl {
    public static class Data {
        public String word;

        public Data(String word) {
            this.word = word;
        }

        @Override
        public String toString() {
            return "Data{ " +
                    "word = " + word  +
                    " } ";
        }
    }

    @Override
    public String read(BufferedReader sockRead) {
        String result = null;
        try {
            result = sockRead.readLine();
            //System.out.println(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void send(PrintWriter sockOut, String message) {
        sockOut.println(message);
    }
}
