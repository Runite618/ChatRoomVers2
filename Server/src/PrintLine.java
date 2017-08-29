
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
public class PrintLine extends FXMLDocumentController implements Runnable {

    private ClientThread alElement;
    private Integer count;
    private DataInputStream sInput;
    private ArrayList<ClientThread> al;
    private ArrayList<String> usersOnline;

    public PrintLine(ClientThread alElement, Integer count, ArrayList<ClientThread> al, ArrayList<String> usersOnline) {
        this.alElement = alElement;
        this.count = count;
        this.al = al;
        this.usersOnline = usersOnline;
    }

    @Override
    public void run() {
        boolean done = false;
        try {
            alElement.getOos().writeObject(usersOnline);
        } catch (IOException ex) {
            Logger.getLogger(PrintLine.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (!done) {
            try {
                sInput = alElement.getSInput();
                String message = sInput.readUTF();
                System.out.println(message);
                for (ClientThread alElement : al) {
                    alElement.getPrintWriter().println(message);
                    alElement.getPrintWriter().flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(PrintLine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
