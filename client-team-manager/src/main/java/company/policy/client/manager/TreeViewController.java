package company.policy.client.manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TreeViewController {

    @FXML
    private Label lblPolicyQuestionMainHeader;
    @FXML
    private TextArea treeViewTextArea;

    @FXML
    void initialize() {

    }

    public TextArea getTreeViewTextArea() {
        return treeViewTextArea;
    }

    @FXML
    void closeTreeView(ActionEvent event) {
        Stage stage = (Stage) treeViewTextArea.getScene().getWindow();
        stage.close();
    }
}
