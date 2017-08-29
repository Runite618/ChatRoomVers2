
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public final class ThreadServer extends ClientThread implements Runnable {

    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private ServerSocket serverSocket2;
    private static final int portNumber = 4;
    private static final int portNumber2 = 6;
    private ArrayList<ClientThread> al = new ArrayList<ClientThread>();
    private ArrayList<String> usersOnline = new ArrayList<String>();
    private Integer count = 0;

    public ThreadServer() {
    }

    public static int getPortNumber() {
        return portNumber;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public DataInputStream getStreamIn() {
        return streamIn;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setStreamIn(DataInputStream streamIn) {
        this.streamIn = streamIn;
    }

    public void setStreamOut(DataOutputStream streamOut) {
        this.streamOut = streamOut;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void close() throws IOException {
        if (clientSocket != null) {
            clientSocket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
    }

    public void clientToServer() throws IOException {
        setServerSocket(new ServerSocket(portNumber));
        System.out.println("Server started: " + getServerSocket());
        System.out.println("Waiting for a client ...");
        setClientSocket(serverSocket.accept());
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
            serverSocket2 = new ServerSocket(portNumber2);
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean flag = true;
        Scanner reader = new Scanner(System.in);
        outer:
        while (flag) {
            try {
                System.out.println("Server waiting for clients on port: " + portNumber);
                System.out.println("Server waiting for clients on port: " + portNumber2);
                Socket socket = serverSocket.accept();
                Socket socket2 = serverSocket2.accept();
                Class<? extends LoginController.User> user = LoginController.User.class;
                ClientThread t = null;
                try {
                    t = new ClientThread(socket, socket2, user);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                String username = (String) t.getOis().readObject();
                System.out.println(username + " has connected.");
                usersOnline.add(username);
                al.add(t);
                t.start();
                System.out.println("Continue adding clients? [y/n]");
                String answer = reader.nextLine();
                count++;
                if (answer.contains("n")) {
                    flag = false;
                    break outer;
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < al.size(); i++) {
            Thread printLineThread = new Thread(new PrintLine(al.get(i), count, al, usersOnline));
            printLineThread.start();
        }
    }
}
