
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
public final class ThreadServer extends FXMLDocumentController implements Runnable{
    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private static final int portNumber = 3306;
    private static Thread t1;
    private ArrayList<ClientThread> al = new ArrayList<ClientThread>();
    
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
    
    public ThreadServer() throws NoSuchMethodException
    {   
        try {
//            System.out.println("Binding to port " + getPortNumber() + ", please wait  ...");
//            clientToServer();
//            open();
            start();
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

    public void start() throws IOException, NoSuchMethodException{
        ServerSocket serverSocket = new ServerSocket(portNumber);
        
        while(true)
        {
            try {
                System.out.println("Server waiting for clients on port: " + portNumber);
                Socket socket = serverSocket.accept();
                Class<? extends UserName> userName = UserName.class;
                ClientThread t = new ClientThread(socket, userName);
                al.add(t);
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void run()
    {
        try {
            start();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
