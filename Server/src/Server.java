
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matth
 */
public class Server extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException{
        ThreadServer threadServer = new ThreadServer();
        threadServer.startServer(primaryStage);
        ChatServer chatServer = new ChatServer(threadServer.getServerSocket());
    }
}
