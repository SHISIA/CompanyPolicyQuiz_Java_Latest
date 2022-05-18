package companypolicyquiz_grouplatest.companypolicyquiz_java_latest;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

//public class ClientView implements Runnable{
public class ClientView extends Application implements Initializable, ActionListener, WindowListener {

    //CHAT RELATED ---------------------------
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private ChatClientThread2 client2 = null;
    private String serverName = "localhost";
    private int serverPort = 4444;
    //----------------------------------------

    //java.awt.Label lblWord1, lblWord2, lblMessage;
    //java.awt.TextField txtWord1, txtWord2;
    //java.awt.Button btnSend, btnExit, btnConnect;

    @FXML
    private Button btnConnectClientView;

    @FXML
    private Button btnExitClientView;

    @FXML
    private Button btnSendClientView;

    @FXML
    private Label lblMessage;

    @FXML
    private Label lblPolicyQuestionMainHeader;

    @FXML
    private Label lblQuestionNumber;

    //@FXML
    //private AnchorPane lblStaffName;

    @FXML
    private Label lblYourAnserClient;

    @FXML
    private Label lblYourName;

    @FXML
    private TextField txtAnswerA;

    @FXML
    private TextField txtAnswerB;

    @FXML
    private TextField txtAnswerC;

    @FXML
    private TextField txtAnswerD;

    @FXML
    private TextField txtAnswerE;

    @FXML
    private Label txtQuestion1Client;

    @FXML
    private Label txtQuestion2Client;

    @FXML
    private Label txtQuestion3Client;

    @FXML
    private Label txtQuestion4Client;

    @FXML
    private Label txtQuestion5Client;

    @FXML
    private Label txtQuestionClient;

    @FXML
    private TextField txtQuestionText;

    @FXML
    private TextField txtTopic;

    @FXML
    private Label txtTopicClient;

    @FXML
    private TextField txtWord1;

    @FXML
    private TextField txtWord2;


    @FXML
    private void setBtnExitClientViewAction() {
        // get a handle to the stage
        Stage stage = (Stage) btnExitClientView.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void buttonConnectFunction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) btnConnectClientView.getScene().getWindow();
        connect(serverName, serverPort);
    }

    /*
    @FXML
    protected void handleCloseAction_2(javafx.event.ActionEvent event) {
        Stage stage = (Stage) btnExit_2.getScene().getWindow();
        stage.close();
    }
    */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("client-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //scene.getStylesheets().add("/styles/Styles.css");
        // Java FMXL Program taskbar logo
        // Image icon = new Image("/Icons/quizIcon.png");  // Creates an image Icon
        // stage.getIcons().add(icon);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Company Policy Quiz App Client");
        stage.setScene(scene);
        //HelloController.readFile();
        stage.show();
        stage.setResizable(false);

        //chat server stuff
        this.addWindowListener(this);

        //CHAT RELATED ---------------------------
        getParameters();
        //----------------------------------------
    }

    private void addWindowListener(ClientView clientView) {
    }

    public static void main(String args[])
    {
        launch();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSendClientView)
        {
            send();
            txtWord1.requestFocus();
        }
        if (e.getSource() == btnExitClientView)
        {
            txtWord1.setText(".bye"); //maybe useful
            //txtWord2.setText("");
            //send();
            System.exit(0);
        }
        if (e.getSource() == btnConnectClientView)
        {
            connect(serverName, serverPort);
        }
    }

    public void connect(String serverName, int serverPort)
    {
        println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe)
        {
            println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    private void send()
    {
        try
        {
            streamOut.writeUTF(txtWord1.getText());
            streamOut.flush();
            txtWord1.setText("");
        }
        catch (IOException ioe)
        {
            println("Sending error: " + ioe.getMessage());
            close();
        }
    }

    public void handle(String msg)
    {
        if (msg.equals(".bye"))
        {
            println("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
            System.out.println("Handle: " + msg);
            println(msg);
        }
    }

    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            client2 = new ChatClientThread2(this, socket);
        }
        catch (IOException ioe)
        {
            println("Error opening output stream: " + ioe);
        }
    }

    public void close()
    {
        try
        {
            if (streamOut != null)
            {
                streamOut.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ioe)
        {
            println("Error closing ...");
        }
        client2.close();
        client2.stop();
    }

    void println(String msg)
    {
        //display.appendText(msg + "\n");
        lblMessage.setText(msg);
    }

    //public void getParameters()
    //{
//        serverName = getParameter("host");
//        serverPort = Integer.parseInt(getParameter("port"));

        //serverName = "localhost";
        //serverPort = 4444;
    //}



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    //<editor-fold defaultstate="collapsed" desc="WindowListener">
    public void windowClosing(WindowEvent we)
    {
        System.exit(0);
    }

    public void windowIconified(WindowEvent we)
    {
    }

    public void windowOpened(WindowEvent we)
    {
    }

    public void windowClosed(WindowEvent we)
    {
    }

    public void windowDeiconified(WindowEvent we)
    {
    }

    public void windowActivated(WindowEvent we)
    {
    }

    public void windowDeactivated(WindowEvent we)
    {
    }


//</editor-fold>
}
