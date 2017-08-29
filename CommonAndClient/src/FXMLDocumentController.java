/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author matth
 */
public class FXMLDocumentController extends LoginController implements Initializable, Runnable {

    @FXML
    private Label label;

    @FXML
    private Button send;

    @FXML
    private TableColumn<LoginController.User, String> users;

    @FXML
    private TableView<LoginController.User> usersView;

    @FXML
    private TextField chatMessage;

    @FXML
    private TextArea chatRoom;

    @FXML
    public void onEnter(ActionEvent ae) throws IOException
    {
        send(chatClient);
        chatMessage.setText("");
    }
    
    private ChatClient chatClient;
    
    private static LoginController.User UserName;
    
    public static LoginController.User getUser() {
        return UserName;
    }

    public void setUser(LoginController.User userName) {
        this.UserName = userName;
    }

    public ChatClient getChatClient()
    {
        return chatClient;
    }
    
    public void setChatClient(ChatClient chatClient)
    {
        this.chatClient = chatClient;
    }
    
    public TextArea getChatRoom()
    {
        return chatRoom;
    }
    
    public void setChatRoom(TextArea chatRoom)
    {
        this.chatRoom = chatRoom;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            users.prefWidthProperty().bind(usersView.widthProperty());
            chatMessage.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
            chatRoom.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
            users.setCellValueFactory(new PropertyValueFactory<LoginController.User, String>("user"));
            chatClient = new ChatClient("localhost", 4, 6, usersView);
            Thread clientThread = new Thread(chatClient);
            clientThread.start();
            Thread FXMLDocumentThread = new Thread(this);
            FXMLDocumentThread.start();
//            Thread serverThread = new Thread(new ListenFromServer(getChatClient()));
//            serverThread.start();
            send.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override
                public void handle(ActionEvent event) {
                    send(chatClient);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void send(ChatClient chatClient)
    {
        try {
            DataInputStream console = new DataInputStream(new ByteArrayInputStream(chatMessage.getText().getBytes(StandardCharsets.UTF_8)));
            String line = "";
            line = chatClient.send(console);
            if(line.contains(".bye"))
            {
//               chatClient.stop();
               System.exit(0);
            }
            chatMessage.setText("");
            String wholeLine = UserName.getUser() + ": " + line;
            chatClient.writeToUTF(wholeLine);
//            chatRoom.appendText(wholeLineN);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                chatRoom.appendText(chatClient.getIn().readLine() + "\n");
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
