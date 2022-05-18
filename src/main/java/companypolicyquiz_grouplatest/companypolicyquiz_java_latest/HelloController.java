package companypolicyquiz_grouplatest.companypolicyquiz_java_latest;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
//import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;

//import javax.swing.*;
//import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

import java.net.URL;
import java.util.*;

//Source relating to Chat:
//  Creating a simple Chat Client/Server Solution
//  http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html

//CHAT RELATED ---------------------------
import java.net.*;
//----------------------------------------

public class HelloController implements Initializable, ActionListener, WindowListener {

    private double xOffset = 0;
    private double yOffset = 0;
    ObservableList<PolicyQuestionModel> fileReadData = FXCollections.observableArrayList(); // JavaFX version of Arraylist

    //CHAT RELATED ---------------------------
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private ChatClientThread1 client = null;
    private String serverName = "localhost";
    private int serverPort = 4444;

    //java.awt.Label lblWord1, lblWord2, lblMessage;
    java.awt.TextField txtWord1, txtWord2;
    //java.awt.Button btnSend;

    @FXML
    private Label lblMessage;

    @FXML
    private VBox VboxMain;

    @FXML
    private TableView<PolicyQuestionModel> tableVeiw; // Main Table View

    @FXML
    private TableColumn<PolicyQuestionModel, IntegerProperty> questionNumberColumn;

    @FXML
    private TableColumn<PolicyQuestionModel, StringProperty> subTopicColumn;

    @FXML
    private TableColumn<PolicyQuestionModel, StringProperty> topicColumn;

    @FXML
    private TextField txtQuestionText;

    @FXML
    private TextField txtTopic;

    @FXML
    private TextField txtSubTopic;

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
    private Label lblQuestionNumber;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnExit_2;


    @FXML
    private Button btnConnect;


    @FXML
    private Button btnSend;


    @FXML
    private Button btnMinimise;

    @FXML
    private Button btnQuestionNumberSort;


    @FXML
    public void buttonQN_Sort(ActionEvent event) {
        Stage stage = (Stage) btnQuestionNumberSort.getScene().getWindow();
        BubbleSort(fileReadData);
        //tableVeiw.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableVeiw.refresh();
    }

    @FXML
    private Button btnTopicSort;


    @FXML
    public void buttonInsertion_Sort(ActionEvent event) {
        Stage stage = (Stage) btnTopicSort.getScene().getWindow();
        InsertionSort(fileReadData);
        tableVeiw.refresh();
    }

    @FXML
    private Button btnSubTopicSort;

    @FXML
    public void btnSelectionSort(ActionEvent event) {
        Stage stage = (Stage) btnSubTopicSort.getScene().getWindow();
        SelectionSort(fileReadData);
        tableVeiw.refresh();
    }

    @FXML
    private TextField txtSearchField;

    @FXML
    private Button btnOpenFile;

    public void buttonConnectFunction(ActionEvent event) {
        Stage stage = (Stage) btnConnect.getScene().getWindow();
            connect(serverName, serverPort);
    }


    @FXML
    protected void handleCloseAction(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();

        //Chat stuff
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
        client.close();
        client.stop();
    }

    @FXML
    protected void handleCloseAction_2(ActionEvent event) {
        Stage stage = (Stage) btnExit_2.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleMinimiseAction(ActionEvent event) {
        Stage stage = (Stage) btnMinimise.getScene().getWindow();
        stage.setIconified(true);
    }

    //The following 2 items control your ability to move the window when you click and hold the mouse button
    /** --------------------------------------------------------
     * <h3>Purpose: Main Panel Movement - When click and hold mouse button</h3>
     *          <b>handleClickAction - Main VboxMain Panel<b>Declare our label variable = </b>
     *          <p>private double xOffset</p>
     *          <p>private double yOffset</p>
     *          <p>Used for moving the window when click and hold mouse</P>
     *          <p></p>
     *          <b>Gets x and y movement values from the window screen/b>
     *          <p></p>
     *          <p><b>Example Implementation: </b></p>
     *          <p></p>
     *          //@FXML
     *          protected void handleClickAction(MouseEvent event) {
     *          Stage stage = (Stage) VboxMain.getScene().getWindow();
     *          xOffset = stage.getX() - event.getScreenX();
     *          yOffset = stage.getY() - event.getScreenY();
     *          }
     *
     *          //@FXML
     *          protected void handleMovementAction(MouseEvent event) {
     *          Stage stage = (Stage) VboxMain.getScene().getWindow();
     *          stage.setX(event.getScreenX() + xOffset);
     *          stage.setY(event.getScreenY() + yOffset);
     *          }
     * ----------------------------------------------------------
     */
    @FXML
    protected void handleClickAction(MouseEvent event) {
        Stage stage = (Stage) VboxMain.getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }
    @FXML
    protected void handleMovementAction(MouseEvent event) {
        Stage stage = (Stage) VboxMain.getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    /** ---------------------------------------------------------------------------------------------------------------------------------------
     * <h3>Purpose: When user clicks on table row the text fields are populated</h3>
     *          <b>onTableRowClick - tableView is clicked</b>
     *          <p>Used for populating text fields when user clicks</P>
     *          <p></p>
     *          <b>It populates the text fields with table data that has been read in when user clicks in the table/b>
     *          <p></p>
     *          <p><b>Example Implementation: </b></p>
     *          <p></p>
     *          //@FXML
     *          protected void onTableRowClick(MouseEvent event) {
     *              if(event.getClickCount() == 1)  //Check for click, change to 2 for double click
     *              {
     *                  txtTopic.setText(tableVeiw.getSelectionModel().getSelectedItem().getPolicyTopic());
     *                  txtSubTopic.setText(tableVeiw.getSelectionModel().getSelectedItem().getPolicyTopic());
     *                  lblQuestionNumber.setText(String.valueOf(tableVeiw.getSelectionModel().getSelectedItem().getPolicyQuestionNumber()));
     *                  txtCorrectAnswer.setText(String.valueOf(tableVeiw.getSelectionModel().getSelectedItem().getCorrectAnswer()));
     *              }
     *          }
     * ----------------------------------------------------------------------------------------------------------------------------------------
     */
    // Display Text Fields with data from item selected in table on mouse click
    @FXML
    protected void onTableRowClick(MouseEvent event) {
        if(event.getClickCount() == 1)  //Check for click, change to 2 for double click
        {
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

    @FXML
    public void BubbleSort(ObservableList<PolicyQuestionModel> arr) {

        for(int j=0; j<arr.size(); j++)
        {
            for(int i=j+1; i<arr.size(); i++)
            {
                if((arr.get(i).policyQuestionNumberProperty().toString()).compareToIgnoreCase((arr.get(j).policyQuestionNumberProperty().toString()))>0)
                {
                    PolicyQuestionModel qn = arr.get(j);
                    arr.set(j, arr.get(i));
                    arr.set(i, qn);
                }
            }
            System.out.println((arr.get(j).policyQuestionNumberProperty().toString()) + " - " + (arr.get(j).policyQuestionNumberProperty().toString()));
        }

        //tableVeiw.add(fileReadData);
        /*
        for(int i = 0; i < ObservableArray.length - 1; i++)
            for(int compare = 0; compare < ObservableArray.length - i - 1; compare++) {
                if(ObservableArray[compare] > ObservableArray[compare+1]) {
                    int temp = ObservableArray[compare];
                    ObservableArray[compare] = ObservableArray[compare + 1];
                    ObservableArray[compare + 1] = temp;
                }
        }
        /*
        tableVeiw.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        questionNumberColumn.setCellValueFactory(new PropertyValueFactory<PolicyQuestionModel, IntegerProperty>("policyQuestionNumber"));
        topicColumn.setCellValueFactory(new PropertyValueFactory<PolicyQuestionModel, StringProperty>("policyTopic"));
        subTopicColumn.setCellValueFactory(new PropertyValueFactory<PolicyQuestionModel, StringProperty>("policySubTopic"));

        tableVeiw.setItems(fileReadData);
        tableVeiw.setEditable(true);
        questionNumberColumn.setEditable(true);
         */
    }

    @FXML   // Insertion Sort Method for Descending Order
    public void InsertionSort(ObservableList<PolicyQuestionModel> num)
    {
        int j; // the number of items sorted so far
        PolicyQuestionModel key; // the item to be inserted
        int i;
        for (j = 1; j < num.size(); j++) // Start with 1 (not 0)
        {
            key = num.get(j);
            for(i = j - 1; (i >= 0) && (num.get(i).policyTopicProperty().toString().compareToIgnoreCase(key.policyTopicProperty().toString()) < 0); i--) // Smaller values are moving up
            {
                num.set(i + 1, num.get(i));
            }
            num.set(i + 1, key); // Put the key in its proper location
        }
        System.out.println((num.get(j).policyTopicProperty().toString()) + " - " + (num.get(j).policyTopicProperty().toString()));

    }


    // Selection Sort
    public void SelectionSort (ObservableList<PolicyQuestionModel> num) {
        int i, j; //first, temp
        PolicyQuestionModel first;
        for (i = 0; i < num.size() - 1; i++) {
            first = num.get(i); //initialize to subscript of first element
            for (j = i + 1; j < num.size(); j++) //locate smallest element between positions 1 and i.
            {
                //if (num[j] < num[first])
                    //first = j;
                if (num.get(j).policySubTopicProperty().toString().compareToIgnoreCase(first.policySubTopicProperty().toString()) > 0) //< num.get(i).policySubTopicProperty().toString())
                {
                    first = num.get(j);
                    //temp = num[first]; //swap smallest found with element in position i.
                    PolicyQuestionModel temp = first;
                    //num[first] = num[i];
                    num.set(j, num.get(i));
                    // num[i] = temp;
                    num.set(i, temp);
                }
            }
            System.out.println((num.get(j).policySubTopicProperty().toString()) + " - " + (num.get(j).policySubTopicProperty().toString()));
        }
    }


    /*
    @FXML
    void addTableViewEntry(ActionEvent event) {
        if(questionNumberColumn.getText() != null && questionNumberColumn.getText().length() > 0) {
            listView.getItems().add(new PolicyQuestionModel(0, "",""));
            //tbl_Question_Number.clear();
        }
    }
    */

    //Chat server stuff
    /*
    public static void main(String[] args)
    {
        Frame myFrame = new ChatClient1();
        myFrame.setSize(470, 170);
        myFrame.setLocation(400, 200);
        myFrame.setResizable(false);
        myFrame.setVisible(true);

    }
    */
    /*
    public ChatClient1()
    {
        setTitle("Word Association Socket Sample - Client 1");
        setBackground(Color.yellow);

        SpringLayout myLayout = new SpringLayout();
        setLayout(myLayout);

        LocateLabels(myLayout);
        LocateTextFields(myLayout);
        LocateButtons(myLayout);

        this.addWindowListener(this);

        //CHAT RELATED ---------------------------
        getParameters();
        //----------------------------------------
    }
    */

    /*
    //<editor-fold defaultstate="collapsed" desc="GUI Construction">
    public void LocateLabels(SpringLayout myLabelLayout)
    {
        lblWord1 = LocateALabel(myLabelLayout, lblWord1, "Word 1: ", 30, 25);
        lblWord2 = LocateALabel(myLabelLayout, lblWord2, "Word 2: ", 30, 50);
        lblMessage = LocateALabel(myLabelLayout, lblMessage, "---------------------------------------------------------------------", 30, 75);
    }
    */

    /*
    public java.awt.Label LocateALabel(SpringLayout myLabelLayout, java.awt.Label myLabel, String LabelCaption, int x, int y)
    {
        myLabel = new java.awt.Label(LabelCaption);
        add(myLabel);
        myLabelLayout.putConstraint(SpringLayout.WEST, myLabel, x, SpringLayout.WEST, this);
        myLabelLayout.putConstraint(SpringLayout.NORTH, myLabel, y, SpringLayout.NORTH, this);
        return myLabel;
    }

    public void LocateTextFields(SpringLayout myTextFieldLayout)
    {
        txtWord1 = LocateATextField(myTextFieldLayout, txtWord1, 20, 130, 25);
        txtWord2 = LocateATextField(myTextFieldLayout, txtWord2, 20, 130, 50);
    }

    public java.awt.TextField LocateATextField(SpringLayout myTextFieldLayout, java.awt.TextField myTextField, int width, int x, int y)
    {
        myTextField = new java.awt.TextField(width);
        add(myTextField);
        myTextFieldLayout.putConstraint(SpringLayout.WEST, myTextField, x, SpringLayout.WEST, this);
        myTextFieldLayout.putConstraint(SpringLayout.NORTH, myTextField, y, SpringLayout.NORTH, this);
        return myTextField;
    }

    public void LocateButtons(SpringLayout myButtonLayout)
    {
        btnConnect = LocateAButton(myButtonLayout, btnConnect, "Connect", 320, 25, 80, 25);
        btnSend = LocateAButton(myButtonLayout, btnSend, "Send", 320, 50, 80, 25);
        btnExit = LocateAButton(myButtonLayout, btnExit, "Exit", 320, 75, 80, 25);
    }

    public java.awt.Button LocateAButton(SpringLayout myButtonLayout, java.awt.Button myButton, String ButtonCaption, int x, int y, int w, int h)
    {
        myButton = new java.awt.Button(ButtonCaption);
        add(myButton);
        myButton.addActionListener(this);
        myButtonLayout.putConstraint(SpringLayout.WEST, myButton, x, SpringLayout.WEST, this);
        myButtonLayout.putConstraint(SpringLayout.NORTH, myButton, y, SpringLayout.NORTH, this);
        myButton.setPreferredSize(new Dimension(w, h));
        return myButton;
    }
     */
//</editor-fold>

    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        if (e.getSource() == btnSend)
        {
            send();
            //txtWord1.requestFocus();
        }

        if (e.getSource() == btnExit)
        {
            txtWord1.setText(".bye"); //maybe useful
            //txtWord2.setText("");
            send();
            System.exit(0);
        }

        if (e.getSource() == btnConnect)
        {
            //wordList[currentAssocWord] = new AssocData("START");
            connect(serverName, serverPort);
        }
        if(e.getSource() == btnQuestionNumberSort)
        {
            //BubbleSort(fileReadData);
            //BubbleSort(fileReadData);
        }
        // BUTTON BINARY SEARCH -----------------------------------
        /*
        if (e.getSource() == btnBinarySearch)
        {
            boolean found = false;
            int i = 0;
            try {
                while (i < numberOfEntries && found == false)
                {
                    if(TrackerInfo[i].getPersonName().equals(txtFind.getText().toUpperCase()))
                    {
                        found = true;
                    }
                    i++;
                }
                if (found)
                {
                    int result = Arrays.binarySearch(sortArray, 0, numberOfEntries, txtFind.getText().toUpperCase());
                    txtDisplayArea.setForeground(new Color(25, 35, 46));
                    //txtDisplayArea.setForeground(new Color(223, 231, 237));
                    txtDisplayArea.setBackground(new Color(52, 63, 95));
                    txtDisplayArea.setFont(new Font("ROMAN_BASELINE", Font.BOLD, 10));
                    txtDisplayArea.setVisible(true);
                    txtDisplayArea.append("\nBinary Search result = " + result);
                }
                else if (txtFind.getText() != "")
                {
                    JOptionPane myJoptionPane = new JOptionPane("Please try again a valid name has not been \nentered into the FIND text field or the name \nentered does not exist",JOptionPane.WARNING_MESSAGE);
                    JDialog jd;
                    myJoptionPane.setBackground(new Color(52, 63, 95));   //outer edge is behind text overlay
                    myJoptionPane.setForeground(new Color(25, 35, 46));

                    //myJoptionPane.setForeground(new Color(223, 231, 237));   //white text
                    myJoptionPane.setFont(new Font("ROMAN_BASELINE", Font.BOLD, 14));
                    //myJoptionPane.setFont(new Font("Serif", Font.ITALIC, 18));
                    jd = myJoptionPane.createDialog(this, "Warning Invalid Search Entry");
                    myJoptionPane.setOpaque (true);
                    myJoptionPane.setVisible(true);
                    getComponents(myJoptionPane);
                    jd.setVisible(true);
                }
            }
            catch(Exception g)
            {
                JOptionPane myJoptionPane = new JOptionPane("Please try again that is not a valid entry",JOptionPane.WARNING_MESSAGE);
                JDialog jd;
                getComponents(myJoptionPane);
                myJoptionPane.setBackground(new Color(52, 63, 95));
                myJoptionPane.setFont(new Font("Serif", Font.ITALIC, 15));
                myJoptionPane.setForeground(Color.white);
                jd = myJoptionPane.createDialog(this, "Exception Error");
                jd.setFont(new Font("ROMAN_BASELINE", Font.BOLD, 20));
                jd.setVisible(true);
            }
        }
        */
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

    //handle method is recieving and processing message
    public void handle(String msg)
    {
        if (msg.equals(".bye"))
        {
            println("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
            println(msg);
        }
    }

    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            client = new ChatClientThread1(this, socket);
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
        client.close();
        client.stop();
    }


    void println(String msg)
    {
        //display.appendText(msg + "\n");
        lblMessage.setText(msg);    //this prints message whats happening
    }

    public void getParameters()
    {
//        serverName = getParameter("host");    //can set your own host here
//        serverPort = Integer.parseInt(getParameter("port"));  //can set your own port here

        serverName = "localhost";
        serverPort = 4444;
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


    public ObservableList<PolicyQuestionModel> list = FXCollections.observableArrayList(
            // For Hard coding entrys

            //new PolicyQuestionModel()
            /*
            new PolicyQuestionModel(11, "OHS","Working At Heights"),
            new PolicyQuestionModel(12, "Copyright","Client Copyright Materials"),
            new PolicyQuestionModel(13, "Privacy","Staff Details"),
            new PolicyQuestionModel(21, "OHS","Hazards"),
            new PolicyQuestionModel(36, "Copyright","Purchasing Copyright Materials"),
            new PolicyQuestionModel(41, "OHS","Incidents")
            new PolicyQuestionModel(42, "Copyright","Internal Copyright Materials"),
            new PolicyQuestionModel(50, "OHS","Confined Space Work"),
            new PolicyQuestionModel(56, "Privacy","Privacy Policy Location"),
            new PolicyQuestionModel(61, "OHS","Safety Reporting"),
            new PolicyQuestionModel(62, "Copyright","Company Copyright Materials List"),
            new PolicyQuestionModel(70, "OHS","Confined Space Work"),
            new PolicyQuestionModel(71, "Privacy","Company Computer Privacy"),
            new PolicyQuestionModel(82, "Copyright","Copyright External Material"),
            new PolicyQuestionModel(90, "OHS","Safety Induction")
            */
    );


    // Seeds Table
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        int numberOfEntrys;
        String fileName = "PerfectPoliciesQuizData.txt";


        // Reading in of the data file
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            numberOfEntrys = 0;
            String lineInDataFile;

            for (int i = 0; i < 10; i++) {
                lineInDataFile = br.readLine();
            }
            while ((lineInDataFile = br.readLine()) != null) {

                PolicyQuestionModel policyquestionmodel = new PolicyQuestionModel();

                policyquestionmodel.setPolicyQuestionNumber(Integer.parseInt(lineInDataFile));
                policyquestionmodel.setPolicyTopic(br.readLine());
                policyquestionmodel.setPolicySubTopic(br.readLine());
                policyquestionmodel.setPolicyQuestionText(br.readLine());
                policyquestionmodel.setAnswerOptionA(br.readLine());
                policyquestionmodel.setAnswerOptionB(br.readLine());
                policyquestionmodel.setAnswerOptionC(br.readLine());
                policyquestionmodel.setAnswerOptionD(br.readLine());
                policyquestionmodel.setAnswerOptionE(br.readLine());
                policyquestionmodel.setCorrectAnswer(Integer.parseInt(br.readLine()));

                fileReadData.add(policyquestionmodel);

                numberOfEntrys++;

            }
            br.close();
            in.close();
            fstream.close();

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
        FilteredList<PolicyQuestionModel> filteredData = new FilteredList<>(fileReadData, b-> true);
        txtSearchField.textProperty().addListener((Observable, oldValue, newValue) ->
                filteredData.setPredicate(PolicyQuestionModel -> {

            // If no search value then display all records or whatever records it currently has. no changes.
            if(newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }

            String searchKeyword = newValue.toLowerCase();
            //No match has been found
            if(PolicyQuestionModel.getPolicyTopic().toLowerCase().contains(searchKeyword)) {     //toString().indexOf(searchKeyword)
                return true;    //Means we found a match in policy topic
            } else
                return PolicyQuestionModel.getPolicySubTopic().toLowerCase().contains(searchKeyword);
                    // PolicyQuestionModel.getPolicySubTopic().toLowerCase().indexOf(searchKeyword) > -1) {
        }));

        SortedList<PolicyQuestionModel> sortedData = new SortedList<>(filteredData);
        // Bind sorted result with table view
        sortedData.comparatorProperty().bind(tableVeiw.comparatorProperty());
        tableVeiw.setItems(sortedData);
    }

    public void mousePressed(javafx.scene.input.MouseEvent mouseEvent) {
    }

    @FXML
    public void mouseClicked(MouseEvent event) {
        Stage stage = (Stage) btnExit_2.getScene().getWindow();
    }

    /*
    @FXML
    public void mouseClicked(MouseEvent event) {
        //Stage stage = (Stage) btnExit_2.getScene().getWindow();
        //int rowId = tbl_Main_PolicyQuestionsTable.getSelectedRow();
        //displayQuestion(rowId);

        String textAreaString = "";

        ObservableList list = listView.getSelectionModel().getSelectedItems();

        for (Object item : list) {
            textAreaString += String.format("%s%n", (String) item);
        }

        this.myTextArea.setText(textAreaString);
    }
    */
    /*
    public void fileReadHandler()
    {
        int numberOfEntrys;
        String fileName = "PerfectPoliciesQuizData.txt";
        ObservableList<PolicyQuestionModel> fileReadData = FXCollections.observableArrayList(); // JavaFX version of Arraylist

        // Reading in of the data file
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            numberOfEntrys = 0;
            String lineInDataFile;

            for (int i = 0; i < 10; i++) {
                lineInDataFile = br.readLine();
        }
        while ((lineInDataFile = br.readLine()) != null) {

            PolicyQuestionModel policyquestionmodel = new PolicyQuestionModel();

            policyquestionmodel.setPolicyQuestionNumber(Integer.parseInt(lineInDataFile));
            policyquestionmodel.setPolicyTopic(br.readLine());
            policyquestionmodel.setPolicySubTopic(br.readLine());
            policyquestionmodel.setPolicyQuestionText(br.readLine());
            policyquestionmodel.setAnswerOptionA(br.readLine());
            policyquestionmodel.setAnswerOptionB(br.readLine());
            policyquestionmodel.setAnswerOptionC(br.readLine());
            policyquestionmodel.setAnswerOptionD(br.readLine());
            policyquestionmodel.setAnswerOptionE(br.readLine());
            policyquestionmodel.setCorrectAnswer(Integer.parseInt(br.readLine()));

            fileReadData.add(policyquestionmodel);
            numberOfEntrys++;
        }
        br.close();
        in.close();
        fstream.close();
        } catch (IOException e) {
            System.err.println("Error Occurred when attempting to read in the file: " + e.getMessage());
        }
    }
    */
}