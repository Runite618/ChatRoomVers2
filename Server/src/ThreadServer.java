
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ThreadServer extends SuperClass{
    public ThreadServer() throws IOException{
        super(1030);
    }
    
    public void startServer(Stage stage)
    {
        try {
            SuperClass server = new SuperClass(1030);
            System.out.println("Binding to port " + getPortNumber() + ", please wait  ...");
            clientToServer(getServerSocket());
            open(getClientSocket());
            close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
