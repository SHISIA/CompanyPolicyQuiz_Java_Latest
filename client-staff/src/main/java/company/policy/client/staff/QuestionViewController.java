package company.policy.client.staff;

import company.policy.client.core.ChatClient;
import company.policy.client.core.ChatClientThread;
import company.policy.client.core.PolicyQuestionModel;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuestionViewController implements ChatClient {

    private final String serverName = "localhost";
    private final int serverPort = 4444;
    private Socket socket;
    private DataOutputStream streamOut;
    private ChatClientThread client;
    private PolicyQuestionModel model;
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
    @FXML
    private AnchorPane lblStaffName;
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
    private TextField txtWord11;

    @Override
    public void stop() {
        if (Platform.isFxApplicationThread()) {
            doStop();
        } else {
            Platform.runLater(this::doStop);
        }
    }

    private void doStop() {
        try {
            if (streamOut != null) {
                streamOut.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ioe) {
            println("Error closing ...");
        }

        if (client != null) {
            client.close();
            client.stop();
        }
    }

    @Override
    public void handle(String msg) {
        if (Platform.isFxApplicationThread()) {
            doHandle(msg);
        } else {
            Platform.runLater(() -> doHandle(msg));
        }
    }

    private void doHandle(String msg) {
        if (msg.equals(".bye")) {
            println("Good bye. Press EXIT button to exit ...");
            stop();
        } else {
            msg = msg.substring(msg.indexOf(":") + 1, msg.length());

            try {
                txtWord1.setText("");
                txtWord11.setText("");
                
                model = PolicyQuestionModel.valueOf(msg);

                model.staffNameProperty().bind(txtWord11.textProperty());
                txtTopic.textProperty().bind(model.policyTopicProperty());
                txtQuestionText.textProperty().bind(model.policyQuestionTextProperty());
                txtAnswerA.textProperty().bind(model.answerOptionAProperty());
                txtAnswerB.textProperty().bind(model.answerOptionBProperty());
                txtAnswerC.textProperty().bind(model.answerOptionCProperty());
                txtAnswerD.textProperty().bind(model.answerOptionDProperty());
                txtAnswerE.textProperty().bind(model.answerOptionEProperty());


                model.givenAnswerProperty().bind(
                        Bindings.createIntegerBinding(
                                () -> {
                                    int answer = 0;

                                    try {
                                        answer = Integer.parseInt(txtWord1.getText());
                                        //println("");
                                    } catch (NumberFormatException ex) {
                                        //println("Couldn't set the selected answer");
                                    }

                                    return (answer > 0 && answer < 6) ? answer : 0;
                                },
                                txtWord1.textProperty())
                );

            } catch (Exception ex) {
                println(ex.getMessage());
            }
        }
    }

    @FXML
    public void initialize() {
        btnSendClientView.setOnAction(ev -> {

            int answer = 0;

            try {
                answer = Integer.parseInt(txtWord1.getText());
                println("");
            } catch (NumberFormatException ex) {
                println("Couldn't set the selected answer");
            }

            answer = (answer > 0 && answer < 6) ? answer : 0;

            if (model != null && answer != 0 && txtWord11.getText() != null && !txtWord11.getText().trim().isEmpty()) {
                send(model.toString());
            }

            ev.consume();
        });
    }

    @FXML
    void buttonConnectFunction(ActionEvent event) {
        connect(serverName, serverPort);
    }

    @FXML
    void setBtnExitClientViewAction(ActionEvent event) {
        //Chat stuff
        stop();

        Stage stage = (Stage) btnExitClientView.getScene().getWindow();
        stage.close();
    }

    private void connect(String serverName, int serverPort) {
        println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();

            btnSendClientView.setDisable(false);
            btnConnectClientView.setDisable(true);
        } catch (UnknownHostException uhe) {
            println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    private void open() {
        try {
            streamOut = new DataOutputStream(socket.getOutputStream());
            client = new ChatClientThread(this, socket);
        } catch (IOException ioe) {
            println("Error opening output stream: " + ioe);
        }
    }

    private void send(String answer) {
        try {
            streamOut.writeUTF(answer);
            streamOut.flush();
        } catch (IOException ioe) {
            println("Sending error: " + ioe.getMessage());
            stop();
        }
    }

    private void println(String msg) {
        lblMessage.setText(msg);
    }

}
