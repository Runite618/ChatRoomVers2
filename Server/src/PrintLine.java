
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
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

    private ArrayList<ClientThread> al;
    private Integer count;

    public PrintLine(ArrayList<ClientThread> al, Integer count) {
        this.al = al;
        this.count = count;
    }

    @Override
    public void run() {
        boolean done = false;
        List<ClientThread> lines = new ArrayList<ClientThread>();
        while (!done) {
            al.stream().filter(x -> x.sInput == x.sInput).map(x -> x.getSInput()).forEach(sInput -> {
                try {
                    System.out.println(sInput.readUTF());
                } catch (IOException ex) {
                    Logger.getLogger(PrintLine.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
}
