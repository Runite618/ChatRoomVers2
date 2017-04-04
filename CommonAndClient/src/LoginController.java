/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
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
public class LoginController implements Initializable {

    @FXML
    private TextField userField;

    @FXML
    private HBox userBox;

    @FXML
    private Button enterChatRoom;

    @FXML
    private Label user;

    @FXML
    private Label userEmpty;

    public static class UserName {

        public static SimpleStringProperty user;

        public UserName(String user) {
            this.user = new SimpleStringProperty(user);
        }
        
        public static String getUserName()
        {
            return UserName.user.get();
        }
        
        public void setUserName(String user)
        {
            this.user.set(user);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        enterChatRoom.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (userField.getText() != null && !userField.getText().isEmpty()) {
                    Stage stage;
                    Parent root = null;

                    UserName userName = new UserName(userField.getText());
                    
                    stage = (Stage) enterChatRoom.getScene().getWindow();
                    try {
                        root = (Parent) controllerFactory(userName).load(this.getClass().getResourceAsStream("FXMLDocument.fxml"));
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
        });
    }

    public FXMLLoader controllerFactory(UserName userName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> controllerClass) {
                if (controllerClass == FXMLDocumentController.class) {
                    FXMLDocumentController controller = new FXMLDocumentController();
                    // Edit object variables here.
                    controller.setUser(userName);
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
}
