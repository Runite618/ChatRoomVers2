import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ChatClient extends FXMLDocumentController implements Runnable {

    private Socket socket;
    private Socket socket2;
    private BufferedReader consoleReader;
    private BufferedReader in;
    private DataOutputStream streamOut;
    private DataInputStream streamIn;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private TableView<LoginController.User> usersView;
    private ObservableList<LoginController.User> oUsersOnlineUser;
    private ArrayList<String> usersOnline;
    private ArrayList<LoginController.User> usersOnlineUser;

    public ChatClient(String serverName, int serverPort, int serverPort2, TableView<LoginController.User> usersView) throws IOException {
        System.out.println("Establishing connection. Please wait ...");
        this.socket = new Socket(serverName, serverPort);
        this.socket2 = new Socket(serverName, serverPort2);
        System.out.println("Connected: " + socket);
        System.out.println("Connected: " + socket2);
        this.streamOut = new DataOutputStream(socket.getOutputStream());
        this.streamIn = new DataInputStream(socket.getInputStream());
        this.oos = new ObjectOutputStream(socket2.getOutputStream());
        getOos().writeObject(getUser().user);
        this.ois = new ObjectInputStream(socket2.getInputStream());
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.usersView = usersView;
        this.usersOnline = new ArrayList<String>();
        this.usersOnlineUser = new ArrayList<LoginController.User>();
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public void setStreamOut(DataOutputStream streamOut) {
        this.streamOut = streamOut;
    }

    public void setStreamIn(DataInputStream streamIn) {
        this.streamIn = streamIn;
    }

    public BufferedReader getIn() {
        return in;
    }

    public DataOutputStream getStreamOut() {
        return streamOut;
    }

    public DataInputStream getStreamIn() {
        return streamIn;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public String send(InputStream console) throws UnsupportedEncodingException, IOException {
        consoleReader = new BufferedReader(new InputStreamReader(console, "UTF-8"));
        String line = "";
        line = consoleReader.readLine();
        return line;
    }

    public void writeToUTF(String wholeLine) throws IOException {
        streamOut.writeUTF(wholeLine);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        while (true) {
            try {
                usersOnline = (ArrayList<String>) getOis().readObject();
                for (int i = 0; i < usersOnline.size(); i++) {
                    LoginController.User user = new LoginController.User(usersOnline.get(i));
                    usersOnlineUser.add(user);
                }
                oUsersOnlineUser = FXCollections.observableArrayList(usersOnlineUser);
                usersView.setItems(oUsersOnlineUser);
            } catch (IOException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
