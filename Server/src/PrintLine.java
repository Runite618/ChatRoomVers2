
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import java.io.DataInputStream;
import java.io.IOException;
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
public class PrintLine extends Thread {

    private ClientThread alElement;
    private Integer count;
    private DataInputStream sInput;

    public PrintLine(ClientThread alElement, Integer count) {
        this.alElement = alElement;
        this.count = count;
    }

    @Override
    public void run() {
        boolean done = false;
        List<ClientThread> lines = new ArrayList<ClientThread>();
        while (!done) {
            try {
                sInput = alElement.getSInput();
                System.out.println(sInput.readUTF());
            } catch (IOException ex) {
                Logger.getLogger(PrintLine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
