
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
public class ChatServer extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, NoSuchMethodException{
        Thread t1 = new Thread(new ThreadServer());
    }
}
