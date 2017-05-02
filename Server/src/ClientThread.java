import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.random;
import java.net.Socket;
import java.util.UUID;
import static java.util.UUID.randomUUID;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Socket socket;
    protected DataInputStream sInput;
    protected DataOutputStream sOutput;
    private UUID id;
    private Class<? extends LoginController.UserName> userName;
    
    ClientThread()
    {
        
    }
    
    ClientThread(Socket socket, Class<? extends LoginController.UserName> userName) throws IOException, NoSuchMethodException
    {
        this.id = randomUUID();
        this.socket = socket;
        this.userName = userName;
    }
    
    public void run()
    {
        try {
            System.out.println("Thread is attempting to create Data Input/Output Streams");
            sOutput = new DataOutputStream(socket.getOutputStream());
            sInput = new DataInputStream(socket.getInputStream());
            
            System.out.println(userName.toString() + " " + this.id + " just connected.");
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DataOutputStream getSOutput()
    {
        return sOutput;
    }
    
    public void setSOutput(DataOutputStream sOutput)
    {
        this.sOutput = sOutput;
    }
    
    public DataInputStream getSInput()
    {
        return sInput;
    }
    
    public void setSInput(DataInputStream sInput)
    {
        this.sInput = sInput;
    }
}
