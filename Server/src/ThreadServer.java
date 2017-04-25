
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matth
 */
public final class ThreadServer extends Application implements Runnable{
    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private LoginController.UserName userName;
    private static final int portNumber = 3306;
    private static Thread t1;
    
    public static int getPortNumber()
    {
        return portNumber;
    }
    
    public ServerSocket getServerSocket()
    {
        return serverSocket;
    }
    
    public DataInputStream getStreamIn()
    {
        return streamIn;
    }
    
    public Socket getClientSocket()
    {
        return clientSocket;
    }

    public LoginController.UserName getUserName() {
        return userName;
    }
    
    public void setServerSocket(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }
    
    public void setStreamIn(DataInputStream streamIn)
    {
        this.streamIn = streamIn;
    }
    
    public void setStreamOut(DataOutputStream streamOut)
    {
        this.streamOut = streamOut;
    }
    
    public void setClientSocket(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void open() throws IOException {
        setStreamIn(new DataInputStream(new BufferedInputStream(getClientSocket().getInputStream())));
    }

    public void close() throws IOException {
        if (clientSocket != null) {
            clientSocket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
    }
    
    public ThreadServer()
    {
        try {
            System.out.println("Binding to port " + getPortNumber() + ", please wait  ...");
            clientToServer();
            open();
            acceptClient();
            printLine();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clientToServer() throws IOException
    {
        setServerSocket(new ServerSocket(3306));
        System.out.println("Server started: " + getServerSocket());
        System.out.println("Waiting for a client ...");
        setClientSocket(serverSocket.accept());
    }
    
    public void acceptClient() throws IOException
    {
        ChatClient chatClient = new ChatClient("localhost", 3306);
        setStreamOut(chatClient.startClient(getClientSocket()));
        System.out.println("Client accepted: " + getClientSocket());
    }
    
    public void printLine() throws IOException {
        boolean done = false;

        while (!done) {
            String line = getStreamIn().readUTF();
            System.out.println(line);
            done = line.contains(".bye");
        }
    }

    @Override
    public void start(Stage primaryStage){
    }
    
    @Override
    public void run()
    {
        
    }
}
