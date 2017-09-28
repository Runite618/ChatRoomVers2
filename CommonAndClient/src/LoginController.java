/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author matth
 */
public class LoginController extends javafx.application.Application implements Initializable {

    @FXML
    private TextField userField;

    @FXML
    private Button enterChatRoom;
    
    @FXML
    private Label userEmpty;
    
    @FXML
    public void onEnter(ActionEvent ae) throws IOException
    {
        enterChatRoom();
    }

    @Override
    public void start(Stage primaryStage) {
    }

    public class User
    {
        public String user;
        
        public User(String user)
        {
            this.user = user;
        }
        
        public String getUser() {
            return user;
        }
        
        public void setUser(String user) {
            this.user = user;
        }
        
        @Override
        public String toString() {
            return user;
        }
    }
    
    private Stage stage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        enterChatRoom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enterChatRoom();
            }
        });
    }

    public void enterChatRoom()
    {
        String textOfUserField = userField.getText();
        if (textOfUserField != null && !textOfUserField.isEmpty()) {
            Parent root = null;

            User user = new User(textOfUserField);
            user.setUser(textOfUserField);
            
            stage = (Stage) enterChatRoom.getScene().getWindow();
            try {
                root = controllerFactory(user).load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            userEmpty.setText("Please enter a user name");
        }
    }
    
    public FXMLLoader controllerFactory(User user) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> controllerClass) {
                if (controllerClass == FXMLDocumentController.class) {
                    FXMLDocumentController controller = new FXMLDocumentController();
                    // Edit object variables here.
                    controller.setUser(user);
                    return controller;
                } else {
                    try {
                        return controllerClass.newInstance();
                    } catch (Exception exc) {
                        throw new RuntimeException(exc); // just bail
                    }
                }
            }
        });
        return fxmlLoader;
    }
    
    public void platformExit(){
        Platform.exit();
    }
}
