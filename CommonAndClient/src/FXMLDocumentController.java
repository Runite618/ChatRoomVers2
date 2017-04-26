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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FXMLDocumentController extends LoginController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private Button send;

    @FXML
    private TableColumn<LoginController.UserName, String> users;

    @FXML
    private TableView<LoginController.UserName> usersView;

    @FXML
    public TextField chatMessage;

    @FXML
    public TextArea chatRoom;

    @FXML
    public void onEnter(ActionEvent ae) throws IOException
    {
        send(chatClient);
        chatMessage.setText("");
    }
    
    public ChatClient chatClient;
    
    public static LoginController.UserName UserName;

    public static LoginController.UserName getUser() {
        return UserName;
    }

    public void setUser(LoginController.UserName userName) {
        this.UserName = userName;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            users.prefWidthProperty().bind(usersView.widthProperty());
            chatMessage.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
            
            chatRoom.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
            chatClient = new ChatClient("localhost", 3306);
            
            send.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    send(chatClient);
                }
            });
            
            users.setCellValueFactory(new PropertyValueFactory<LoginController.UserName, String>("userName"));
            usersView.getItems().add(getUser());
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
               chatClient.stop();
               System.exit(0);
            }
            chatMessage.setText("");
            String wholeLine = UserName.getUserName() + ": " + line;
            String wholeLineN = UserName.getUserName() + ": " + line + "\n";
            chatClient.writeToUTF(wholeLine);
            chatRoom.appendText(wholeLineN);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
