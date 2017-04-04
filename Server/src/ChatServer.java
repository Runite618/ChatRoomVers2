
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matth
 */
public class ChatServer extends SuperClass{
    private ServerSocket serverSocket;
    
    public ChatServer(ServerSocket serverSocket) throws IOException
    {
        super(1030);
        this.serverSocket = serverSocket;
        clientToServer(this.serverSocket);
        DataInputStream streamIn = open(getClientSocket());
        printLine(streamIn);
    }
}
