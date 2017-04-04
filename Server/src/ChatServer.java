
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.stage.Stage;

public class ChatServer extends Application {

    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private LoginController.UserName userName;
    private int portNumber = 1030;

    public LoginController.UserName getUserName() {
        return userName;
    }

    public ChatServer() {
    }

    public ChatServer(int portNumber) {
        this.portNumber = portNumber;
    }

    public void open() throws IOException {
        streamIn = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
    }

    public void close() throws IOException {
        if (clientSocket != null) {
            clientSocket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
    }

    @Override
    public void start(Stage stage) {
        try {
            ChatServer server = new ChatServer(1030);
            System.out.println("Binding to port " + portNumber + ", please wait  ...");
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started: " + serverSocket);
            System.out.println("Waiting for a client ...");
            clientSocket = serverSocket.accept();
            System.out.println("Client accepted: " + clientSocket);
            open();
            boolean done = false;
            while (!done) {
                String line = streamIn.readUTF();
                System.out.println(line);
                done = line.contains(".bye");
            }
            close();
        } catch (IOException ex) {
            System.out.print("Client has terminated connection");
        }
    }
}
