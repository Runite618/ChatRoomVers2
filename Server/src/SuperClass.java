
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.stage.Stage;

public class SuperClass extends Thread{

    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private LoginController.UserName userName;
    private int portNumber = 1030;
    
    public int getPortNumber()
    {
        return portNumber;
    }
    
    public ServerSocket getServerSocket()
    {
        return serverSocket;
    }
    
    public Socket getClientSocket()
    {
        return clientSocket;
    }

    public LoginController.UserName getUserName() {
        return userName;
    }
    
    public void setPortNumber(int portNumber)
    {
        this.portNumber = portNumber;
    }
    
    public void setServerSocket(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }
    
    public void setClientSocket(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public SuperClass() throws IOException {
        setPortNumber(1030);
        setServerSocket(new ServerSocket(getPortNumber()));
    }

    public SuperClass(int portNumber) {
        this.portNumber = portNumber;
    }

    public DataInputStream open(Socket clientSocket) throws IOException {
        return streamIn = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
    }

    public void close() throws IOException {
        if (clientSocket != null) {
            clientSocket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
    }

    public void clientToServer(ServerSocket serverSocket) throws IOException
    {
        System.out.println("Server started: " + serverSocket);
        System.out.println("Waiting for a client ...");
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            ChatClient chatClient = new ChatClient("localhost", 1030);
            chatClient.startClient(clientSocket);
            System.out.println("Client accepted: " + clientSocket);
        }
    }
    
    public void printLine(DataInputStream streamIn) throws IOException {
        boolean done = false;

        while (!done) {
            String line = streamIn.readUTF();
            System.out.println(line);
            done = line.contains(".bye");
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        
    }
}
