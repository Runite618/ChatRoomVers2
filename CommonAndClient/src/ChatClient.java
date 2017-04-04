import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClient {

    private Socket socket;
    public InputStream console;
    private BufferedReader consoleReader;
    private DataOutputStream streamOut;
    
    public ChatClient(String serverName, int serverPort) throws IOException {
        System.out.println("Establishing connection. Please wait ...");
        socket = new Socket(serverName, serverPort);
        System.out.println("Connected: " + socket);
        start();
    }

    public void start() throws IOException {
        streamOut = new DataOutputStream(socket.getOutputStream());
    }
    
    public String send(InputStream console) throws UnsupportedEncodingException, IOException
    {
        this.console = console;
        consoleReader = new BufferedReader(new InputStreamReader(console, "UTF-8"));
        String line = "";
        line = consoleReader.readLine();
        return line;
    }
    
    public void writeToUTF(String wholeLine) throws IOException
    {
       streamOut.writeUTF(wholeLine);
    }

    public void stop() throws IOException {
        if (console != null) {
            console.close();
        }
        if (streamOut != null) {
            console.close();
        }
        if (socket != null) {
            console.close();
        }
    }
    
    public static void main(String[] args) throws IOException
    {
       ChatClient chatClient = new ChatClient("localhost", 1030);
    }
}
