import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
    private DataInputStream sInput;
    private DataOutputStream sOutput;
    private UUID id;
    private Class<? extends LoginController.User> user;
    private PrintWriter printWriter;
    
    ClientThread()
    {
        
    }
    
    ClientThread(Socket socket, Class<? extends LoginController.User> user) throws IOException, NoSuchMethodException
    {
        this.id = randomUUID();
        this.socket = socket;
        this.user = user;
        this.printWriter = new PrintWriter(socket.getOutputStream());
    }
    
    public void run()
    {
        try {
            System.out.println("Thread is attempting to create Data Input/Output Streams");
            sInput = new DataInputStream(socket.getInputStream());
            sOutput = new DataOutputStream(socket.getOutputStream());
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
    
    public PrintWriter getPrintWriter()
    {
        return printWriter;
    }
    
    public void setPrintWriter(PrintWriter printWriter)
    {
        this.printWriter = printWriter;
    }
}
