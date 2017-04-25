import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import static javafx.application.Application.launch;

public class ChatClient {

    private Socket socket;
    public InputStream console;
    private BufferedReader consoleReader;
    private DataOutputStream streamOut;
    
    public ChatClient(String serverName, int serverPort) throws IOException {
        System.out.println("Establishing connection. Please wait ...");
        socket = new Socket(serverName, serverPort);
        System.out.println("Connected: " + socket);
        streamOut = startClient(socket);
    }

    public void setStreamOut(DataOutputStream streamOut)
    {
        this.streamOut = streamOut;
    }
    
    public DataOutputStream startClient(Socket socket) throws IOException {
        return streamOut = new DataOutputStream(socket.getOutputStream());
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
       launch(args);
    }
}
