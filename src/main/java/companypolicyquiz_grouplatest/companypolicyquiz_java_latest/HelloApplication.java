package companypolicyquiz_grouplatest.companypolicyquiz_java_latest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class HelloApplication extends Application {
    //ArrayList<PolicyQuestionModel> list = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //scene.getStylesheets().add("/styles/Styles.css");
        // Java FMXL Program taskbar logo
        // Image icon = new Image("/Icons/quizIcon.png");  // Creates an image Icon
        // stage.getIcons().add(icon);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Company Policy Quiz App");
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

    private void addWindowListener(HelloApplication helloApplication) {
    }

    public static void main(String[] args) {
        //ArrayList<PolicyQuestionModel> fileData = new ArrayList<>();
        //new fileReadHandler();
        ChatServer server;
        if (args.length != 1)
        {
            //System.out.println("Usage: java ChatServer port");
            server = new ChatServer(4444);
        }
        else
        {
            server = new ChatServer(Integer.parseInt(args[0]));
        }


        launch();
    }
}

