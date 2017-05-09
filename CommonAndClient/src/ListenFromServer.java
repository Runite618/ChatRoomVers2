
import java.io.IOException;
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
public class ListenFromServer extends FXMLDocumentController implements Runnable{
    private ChatClient chatClient;
    
    public ListenFromServer(ChatClient chatClient)
    {
        this.chatClient = chatClient;
    }
    
    public void run()
    {
        while(true)
        {
            try {
                String message = chatClient.getStreamIn().readUTF();
                getChatRoom().appendText(message);
            } catch (IOException ex) {
                Logger.getLogger(ListenFromServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
