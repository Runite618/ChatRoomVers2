import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;

public class ChatClient extends FXMLDocumentController implements Runnable{

    private Socket socket;
    private BufferedReader consoleReader;
    private BufferedReader in;
    private DataOutputStream streamOut;
    private DataInputStream streamIn;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public ChatClient()
    {
        
    }
    
    public ChatClient(String serverName, int serverPort) throws IOException {
        System.out.println("Establishing connection. Please wait ...");
        this.socket = new Socket(serverName, serverPort);
        System.out.println("Connected: " + socket);
        this.streamOut = new DataOutputStream(socket.getOutputStream());
        this.streamIn = new DataInputStream(socket.getInputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void setOos(ObjectOutputStream oos) 
    {
        this.oos = oos;
    }
    
    public void setOis(ObjectInputStream ois) 
    {
        this.ois = ois;
    }
    
    public void setIn(BufferedReader in)
    {
        this.in = in;
    }
    
    public void setStreamOut(DataOutputStream streamOut)
    {
        this.streamOut = streamOut;
    }
    
    public void setStreamIn(DataInputStream streamIn)
    {
        this.streamIn = streamIn;
    }
    
    public BufferedReader getIn() 
    {
        return in;
    }
    
    public DataOutputStream getStreamOut()
    {
        return streamOut;
    }
    
    public DataInputStream getStreamIn()
    {
        return streamIn;
    }
    
    public ObjectOutputStream getOos()
    {
        return oos;
    }
    
    public ObjectInputStream getOis()
    {
        return ois;
    }
    
    public String send(InputStream console) throws UnsupportedEncodingException, IOException
    {
        consoleReader = new BufferedReader(new InputStreamReader(console, "UTF-8"));
        String line = "";
        line = consoleReader.readLine();
        return line;
    }
    
    public void writeToUTF(String wholeLine) throws IOException
    {
       streamOut.writeUTF(wholeLine);
    }
    
    public static void main(String[] args)
    {
       launch(args);
    }
    
    @Override
    public void run()
    {
    }
}
