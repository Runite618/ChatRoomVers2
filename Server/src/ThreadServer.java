
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
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
public final class ThreadServer extends ClientThread implements Runnable{
    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private static final int portNumber = 4;
    private static Thread t1;
    private ArrayList<ClientThread> al = new ArrayList<ClientThread>();
    private Integer count = 0;
    
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
    
    public ThreadServer()
    {
//            System.out.println("Binding to port " + getPortNumber() + ", please wait  ...");
//            clientToServer();
//            open();
        super();
    }
    
    public void clientToServer() throws IOException
    {
        setServerSocket(new ServerSocket(portNumber));
        System.out.println("Server started: " + getServerSocket());
        System.out.println("Waiting for a client ...");
        setClientSocket(serverSocket.accept());
    }
    
    public void acceptClient() throws IOException
    {
        ChatClient chatClient = new ChatClient("localhost", portNumber);
        setStreamOut(chatClient.startClient(getClientSocket()));
        System.out.println("Client accepted: " + getClientSocket());
    }

    @Override
    public void start(){
        
    }
    
    @Override
    public void run()
    {   
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        boolean flag = true;
        Scanner reader = new Scanner(System.in); 
        outer: while(flag)
        {
            try {
                System.out.println("Server waiting for clients on port: " + portNumber);
                Socket socket = serverSocket.accept();
                Class<? extends FXMLDocumentController.User> user = FXMLDocumentController.User.class;
                ClientThread t = null;
                try {
                    t = new ClientThread(socket, user);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                al.add(t);
                t.start();
                System.out.println("Continue adding clients? [y/n]");
                String answer = reader.nextLine();
                count++;
                if (answer.contains("n"))
                {
                    flag = false;
                    break outer;
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < al.size(); i++)
        {
            Thread printLineThread = new Thread(new PrintLine(al.get(i), count));
            printLineThread.start();
        }
    }
}
