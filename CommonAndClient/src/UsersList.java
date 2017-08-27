
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author matth
 */
public class UsersList extends LoginController implements Runnable {
    private TableView<LoginController.User> usersView;
    private ObservableList<LoginController.User> oUsersOnlineUser;
    private ArrayList<String> usersOnline;
    private ArrayList<LoginController.User> usersOnlineUser; 
    private ChatClient chatClient;
    
    public UsersList(TableView<LoginController.User> usersView, ArrayList<String> usersOnline, ChatClient chatClient) {
        this.usersView = usersView;
        this.usersOnline = usersOnline;
        this.usersOnlineUser = new ArrayList<LoginController.User>(); 
        this.chatClient = chatClient;
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                usersOnline = (ArrayList<String>) chatClient.getOis().readObject();
                for(int i = 0; i < usersOnline.size(); i++) {
                    LoginController.User user = new LoginController.User(usersOnline.get(i));
                    usersOnlineUser.add(user);
                }
                oUsersOnlineUser = FXCollections.observableArrayList(usersOnlineUser);
                usersView.setItems(oUsersOnlineUser);
            } catch (IOException ex) {
                Logger.getLogger(UsersList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UsersList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
