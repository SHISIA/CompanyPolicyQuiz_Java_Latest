package company.policy.client.manager;

import company.policy.client.manager.dll.DoublyLinkedList;
import company.policy.client.core.ChatClient;
import company.policy.client.core.ChatClientThread;
import company.policy.client.core.PolicyQuestionModel;
import company.policy.client.manager.bt.BinaryTree;
import company.policy.client.manager.bt.BinaryTreePrinter;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuizViewController implements ChatClient {

    private final ObservableList<PolicyQuestionModel> fileReadData = FXCollections.observableArrayList();
    private final String serverName = "localhost";
    private final int serverPort = 4444;
    private Socket socket;
    private DataOutputStream streamOut;
    private ChatClientThread client;
    private double xOffset;
    private double yOffset;
    @FXML
    private VBox VboxMain;
    @FXML
    private Button btnConnect;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnExit_2;
    @FXML
    private Button btnLoadTableData;
    @FXML
    private Button btnMinimise;
    @FXML
    private Button btnOpenFile;
    @FXML
    private Button btnQuestionNumberSort;
    @FXML
    private Button btnSend;
    @FXML
    private Button btnSubTopicSort;
    @FXML
    private Button btnTopicSort;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblQuestionNumber;
    @FXML
    private TableView<PolicyQuestionModel> tableVeiw;
    @FXML
    private TableColumn<PolicyQuestionModel, IntegerProperty> questionNumberColumn;
    @FXML
    private TableColumn<PolicyQuestionModel, StringProperty> subTopicColumn;
    @FXML
    private TableColumn<PolicyQuestionModel, StringProperty> topicColumn;
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
    private TextField txtCorrectAnswer;
    @FXML
    private TextArea txtQuestionText;
    @FXML
    private TextField txtSearchField;
    @FXML
    private TextField txtSubTopic;
    @FXML
    private TextField txtTopic;
    @FXML
    private TextArea linkedListTArea;
    @FXML
    private TextArea treeTArea;

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
    private final List<PolicyQuestionModel> answeredModels = new ArrayList<>();

    private void doHandle(String msg) {
        if (msg.equals(".bye")) {
            println("Good bye. Press EXIT button to exit ...");
            stop();
        } else {
            msg = msg.substring(msg.indexOf(":") + 1, msg.length());

            PolicyQuestionModel model = PolicyQuestionModel.valueOf(msg);

            if ((model.getStaffName() == null || model.getStaffName().trim().isEmpty()) || model.getGivenAnswer() == 0) {
                return;
            }

            answeredModels.add(model);

            Map<Integer, Set<PolicyQuestionModel>> map = answeredModels.stream().collect(Collectors.groupingBy(m -> m.getPolicyQuestionNumber(), Collectors.toSet()));

            List<PolicyQuestionModel> toPrint = new ArrayList<>();

            for (Map.Entry<Integer, Set<PolicyQuestionModel>> entry : map.entrySet()) {
                Set<PolicyQuestionModel> value = entry.getValue();

                int total = value.size();
                int correctCount = (int) value.stream().filter(m -> m.getGivenAnswer() == m.getCorrectAnswer()).count();

                PolicyQuestionModel pqm = new ArrayList<>(value).get(0);

                pqm.setAttemptedCount(total);
                pqm.setAnsweredCorrectCount(correctCount);

                toPrint.add(pqm);
            }

            DoublyLinkedList dll = new DoublyLinkedList();
            tree = new BinaryTree();

            for (PolicyQuestionModel pqm : toPrint) {
                dll.append(pqm);
            }

            for (PolicyQuestionModel pqm : dll.getAll()) {
                tree.add(pqm);
            }

            linkedListTArea.setText(dll.toString());

            if (order == null) {
                order = new InOrder(tree);
            } else if (order instanceof InOrder) {
                order = new InOrder(tree);
            } else if (order instanceof PreOrder) {
                order = new PreOrder(tree);
            } else {
                order = new PostOrder(tree);
            }

            treeTArea.setText(order.toString());
        }
    }
    private BinaryTree tree;

    private interface Order {

    }

    private static class InOrder implements Order {

        private final List<PolicyQuestionModel> traversedModels;

        private InOrder(BinaryTree tree) {
            this(tree.traverseInOrderWithoutRecursion());
        }

        private InOrder(List<PolicyQuestionModel> traversedModels) {
            this.traversedModels = new ArrayList<>(traversedModels);
        }

        @Override
        public String toString() {
            return traversedModels.stream()
                    .map(value -> String.format("%d - %s (%s)", value.getPolicyQuestionNumber(), value.getPolicyTopic(), value.getPolicySubTopic()))
                    .collect(Collectors.joining(", ", "IN-ORDER: ", ""));
        }

    }

    private static class PreOrder implements Order {

        private final List<PolicyQuestionModel> traversedModels;

        private PreOrder(BinaryTree tree) {
            this(tree.traversePreOrderWithoutRecursion());
        }

        private PreOrder(List<PolicyQuestionModel> traversedModels) {
            this.traversedModels = new ArrayList<>(traversedModels);
        }

        @Override
        public String toString() {
            return traversedModels.stream()
                    .map(value -> String.format("%d - %s (%s)", value.getPolicyQuestionNumber(), value.getPolicyTopic(), value.getPolicySubTopic()))
                    .collect(Collectors.joining(", ", "PRE-ORDER: ", ""));
        }

    }

    private static class PostOrder implements Order {

        private final List<PolicyQuestionModel> traversedModels;

        private PostOrder(BinaryTree tree) {
            this(tree.traversePostOrderWithoutRecursion());
        }

        private PostOrder(List<PolicyQuestionModel> traversedModels) {
            this.traversedModels = new ArrayList<>(traversedModels);
        }

        @Override
        public String toString() {
            return traversedModels.stream()
                    .map(value -> String.format("%d - %s (%s)", value.getPolicyQuestionNumber(), value.getPolicyTopic(), value.getPolicySubTopic()))
                    .collect(Collectors.joining(", ", "POST-ORDER: ", ""));
        }

    }

    @FXML
    private Button displayInOrderBtn;
    @FXML
    private Button displayPreOrderBtn;
    @FXML
    private Button displayPostOrderBtn;
    @FXML
    private Button displayTreeBtn;
    private Order order;

    @FXML
    public void initialize() {
        displayInOrderBtn.setOnAction(e -> {
            if (tree != null) {
                order = new InOrder(tree);
                treeTArea.setText(order.toString());
            }
            e.consume();
        });
        displayPreOrderBtn.setOnAction(e -> {
            if (tree != null) {
                order = new PreOrder(tree);
                treeTArea.setText(order.toString());
            }
            e.consume();
        });
        displayPostOrderBtn.setOnAction(e -> {
            if (tree != null) {
                order = new PostOrder(tree);
                treeTArea.setText(order.toString());
            }
            e.consume();
        });
        displayTreeBtn.setOnAction(e -> {
            try {
                stop();

                Stage stage = (Stage) btnExit.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tree-view.fxml"));
                stage.setScene(new Scene(fxmlLoader.load()));

                TreeViewController controller = fxmlLoader.getController();

                TextArea treeViewTArea = controller.getTreeViewTextArea();

                if (tree != null) {
                    BinaryTreePrinter btp = new BinaryTreePrinter(tree.root);
                    
                    treeViewTArea.setText(btp.print());
                }
            } catch (IOException ex) {
            }

            e.consume();
        });

        // Reading in of the data file
        try ( DataInputStream in = new DataInputStream(getClass().getResourceAsStream("PerfectPoliciesQuizData.txt"));  BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

            String lineInDataFile;

            for (int i = 0; i < 10; i++) {
                lineInDataFile = br.readLine();
            }

            while ((lineInDataFile = br.readLine()) != null) {

                PolicyQuestionModel model = new PolicyQuestionModel();

                model.setPolicyQuestionNumber(Integer.parseInt(lineInDataFile));
                model.setPolicyTopic(br.readLine());
                model.setPolicySubTopic(br.readLine());
                model.setPolicyQuestionText(br.readLine());
                model.setAnswerOptionA(br.readLine());
                model.setAnswerOptionB(br.readLine());
                model.setAnswerOptionC(br.readLine());
                model.setAnswerOptionD(br.readLine());
                model.setAnswerOptionE(br.readLine());
                model.setCorrectAnswer(Integer.parseInt(br.readLine()));

                fileReadData.add(model);
            }

        } catch (IOException e) {
            System.err.println("Error Occurred when attempting to read in the file: " + e.getMessage());
        }

        tableVeiw.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        questionNumberColumn.setCellValueFactory(new PropertyValueFactory<>("policyQuestionNumber"));
        topicColumn.setCellValueFactory(new PropertyValueFactory<>("policyTopic"));
        subTopicColumn.setCellValueFactory(new PropertyValueFactory<>("policySubTopic"));
        // Adjusted this one :
        // subTopicColumn.setCellValueFactory(new PropertyValueFactory<PolicyQuestionModel, StringProperty>("policySubTopic"));

        tableVeiw.setItems(fileReadData);
        tableVeiw.setEditable(true);
        questionNumberColumn.setEditable(true);

        // Initial Filtered List
        FilteredList<PolicyQuestionModel> filteredData = new FilteredList<>(fileReadData, b -> true);
        txtSearchField.textProperty().addListener((Observable, oldValue, newValue)
                -> filteredData.setPredicate(PolicyQuestionModel -> {

                    // If no search value then display all records or whatever records it currently has. no changes.
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();
                    //No match has been found
                    if (PolicyQuestionModel.getPolicyTopic().toLowerCase().contains(searchKeyword)) {     //toString().indexOf(searchKeyword)
                        return true;    //Means we found a match in policy topic
                    } else {
                        return PolicyQuestionModel.getPolicySubTopic().toLowerCase().contains(searchKeyword);
                    }
                    // PolicyQuestionModel.getPolicySubTopic().toLowerCase().indexOf(searchKeyword) > -1) {
                }));

        SortedList<PolicyQuestionModel> sortedData = new SortedList<>(filteredData);
        // Bind sorted result with table view
        sortedData.comparatorProperty().bind(tableVeiw.comparatorProperty());
        tableVeiw.setItems(sortedData);

        btnSend.setOnAction(ev -> {
            PolicyQuestionModel selectedModel = tableVeiw.getSelectionModel().getSelectedItem();

            if (selectedModel != null) {
                send(selectedModel.toString());
            }

            ev.consume();
        });
    }

    @FXML
    void btnSelectionSort(ActionEvent event) {
        selectionSort(fileReadData);
        tableVeiw.refresh();
    }

    @FXML
    void buttonConnectFunction(ActionEvent event) {
        connect(serverName, serverPort);
    }

    @FXML
    void buttonInsertion_Sort(ActionEvent event) {
        insertionSort(fileReadData);
        tableVeiw.refresh();
    }

    @FXML
    void buttonQN_Sort(ActionEvent event) {
        bubbleSort(fileReadData);
        tableVeiw.refresh();
    }

    @FXML
    void handleClickAction(MouseEvent event) {
        Stage stage = (Stage) VboxMain.getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    @FXML
    void handleCloseAction(ActionEvent event) {
        //Chat stuff
        stop();

        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleCloseAction_2(ActionEvent event) {
        handleCloseAction(event);
    }

    @FXML
    void handleMinimiseAction(ActionEvent event) {
        Stage stage = (Stage) btnMinimise.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void handleMovementAction(MouseEvent event) {
        Stage stage = (Stage) VboxMain.getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    void onTableRowClick(MouseEvent event) {
        //Check for click, change to 2 for double click
        if (event.getClickCount() == 1) {
            txtTopic.setText(tableVeiw.getSelectionModel().getSelectedItem().getPolicyTopic());
            txtSubTopic.setText(tableVeiw.getSelectionModel().getSelectedItem().getPolicySubTopic());
            //txtQuestionNumber.setText(String.valueOf(tableVeiw.getSelectionModel().getSelectedItem().getPolicyQuestionNumber()));   //String.value of turns the int into a string similar to toString() method
            txtQuestionText.setText(tableVeiw.getSelectionModel().getSelectedItem().getPolicyQuestionText());
            lblQuestionNumber.setText(String.valueOf(tableVeiw.getSelectionModel().getSelectedItem().getPolicyQuestionNumber()));
            txtAnswerA.setText(tableVeiw.getSelectionModel().getSelectedItem().getAnswerOptionA());
            txtAnswerB.setText(tableVeiw.getSelectionModel().getSelectedItem().getAnswerOptionB());
            txtAnswerC.setText(tableVeiw.getSelectionModel().getSelectedItem().getAnswerOptionC());
            txtAnswerD.setText(tableVeiw.getSelectionModel().getSelectedItem().getAnswerOptionD());
            txtAnswerE.setText(tableVeiw.getSelectionModel().getSelectedItem().getAnswerOptionE());
            txtCorrectAnswer.setText(String.valueOf(tableVeiw.getSelectionModel().getSelectedItem().getCorrectAnswer()));
        }
    }

    private void connect(String serverName, int serverPort) {
        println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();

            btnSend.setDisable(false);
            btnConnect.setDisable(true);
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

    private void send(String modelAsString) {
        try {
            streamOut.writeUTF(modelAsString);
            streamOut.flush();
        } catch (IOException ioe) {
            println("Sending error: " + ioe.getMessage());
            stop();
        }
    }

    private void println(String msg) {
        //display.appendText(msg + "\n");
        //this prints message whats happening
        lblMessage.setText(msg);
    }

    private void selectionSort(ObservableList<PolicyQuestionModel> list) {
        int i, j; //first, temp
        PolicyQuestionModel first;
        for (i = 0; i < list.size() - 1; i++) {
            first = list.get(i); //initialize to subscript of first element
            for (j = i + 1; j < list.size(); j++) //locate smallest element between positions 1 and i.
            {
                //if (num[j] < num[first])
                //first = j;
                if (list.get(j).policySubTopicProperty().toString().compareToIgnoreCase(first.policySubTopicProperty().toString()) > 0) //< num.get(i).policySubTopicProperty().toString())
                {
                    first = list.get(j);
                    //temp = num[first]; //swap smallest found with element in position i.
                    PolicyQuestionModel temp = first;
                    //num[first] = num[i];
                    list.set(j, list.get(i));
                    // num[i] = temp;
                    list.set(i, temp);
                }
            }
            try {
                System.out.println((list.get(j).policySubTopicProperty().toString()) + " - " + (list.get(j).policySubTopicProperty().toString()));
            } catch (Exception e) {
            }
        }
    }

    private void insertionSort(ObservableList<PolicyQuestionModel> list) {
        int j; // the number of items sorted so far
        PolicyQuestionModel key; // the item to be inserted
        int i;
        for (j = 1; j < list.size(); j++) // Start with 1 (not 0)
        {
            key = list.get(j);
            for (i = j - 1; (i >= 0) && (list.get(i).policyTopicProperty().toString().compareToIgnoreCase(key.policyTopicProperty().toString()) < 0); i--) // Smaller values are moving up
            {
                list.set(i + 1, list.get(i));
            }
            list.set(i + 1, key); // Put the key in its proper location
        }
        try {
            System.out.println((list.get(j).policyTopicProperty().toString()) + " - " + (list.get(j).policyTopicProperty().toString()));
        } catch (Exception e) {
        }
    }

    private void bubbleSort(ObservableList<PolicyQuestionModel> list) {
        for (int j = 0; j < list.size(); j++) {
            for (int i = j + 1; i < list.size(); i++) {
                if ((list.get(i).policyQuestionNumberProperty().toString()).compareToIgnoreCase((list.get(j).policyQuestionNumberProperty().toString())) > 0) {
                    PolicyQuestionModel qn = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, qn);
                }
            }
            try {
                System.out.println((list.get(j).policyQuestionNumberProperty().toString()) + " - " + (list.get(j).policyQuestionNumberProperty().toString()));
            } catch (Exception e) {
            }
        }
    }

}
