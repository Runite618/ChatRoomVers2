import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.random;
import java.net.Socket;
import java.util.UUID;
import static java.util.UUID.randomUUID;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matth
 */
public class ClientThread extends Thread {
    Socket socket;
    DataInputStream sInput;
    DataOutputStream sOutput;
    UUID id;
    Class<? extends LoginController.UserName> userName;
    
    ClientThread(Socket socket, Class<? extends LoginController.UserName> userName) throws IOException, NoSuchMethodException
    {
        this.id = randomUUID();
        this.socket = socket;
        this.userName = userName;
        
        System.out.println("Thread is attempting to create Data Input/Output Streams");
        sOutput = new DataOutputStream(socket.getOutputStream());
        sInput = new DataInputStream(socket.getInputStream());
        
        System.out.println(userName.toString() + " just connected.");
    }
}
