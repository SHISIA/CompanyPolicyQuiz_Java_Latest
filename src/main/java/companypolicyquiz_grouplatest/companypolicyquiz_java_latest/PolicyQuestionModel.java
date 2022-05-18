package companypolicyquiz_grouplatest.companypolicyquiz_java_latest;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

public class PolicyQuestionModel {

    private IntegerProperty policyQuestionNumber;
    //private StringProperty policyQuestionNumber;
    private StringProperty policyTopic;
    private StringProperty policySubTopic;

    private StringProperty policyQuestionText;
    private StringProperty answerOptionA;
    private StringProperty answerOptionB;
    private StringProperty answerOptionC;
    private StringProperty answerOptionD;
    private StringProperty answerOptionE;
    private IntegerProperty correctAnswer;


    public PolicyQuestionModel() {
        /*
        this.setPolicyQuestionNumber(1);
        this.setPolicyTopic("");
        this.setPolicySubTopic("");
        this.setPolicyQuestionText("");
        this.setAnswerOptionA("");
        this.setAnswerOptionB("");
        this.setAnswerOptionC("");
        this.setAnswerOptionD("");
        this.setAnswerOptionE("");
        this.setCorrectAnswer(1);
         */
    }

    public PolicyQuestionModel(int policyQuestionNumber, String policyTopic, String policySubTopic, String policyQuestionText,
        String answerOptionA, String answerOptionB, String answerOptionC, String answerOptionD, String answerOptionE, int correctAnswer)
    {
        this.policyQuestionNumber = new SimpleIntegerProperty(policyQuestionNumber);
        //this.policyQuestionNumber = new SimpleStringProperty(policyQuestionNumber);
        this.policyTopic = new SimpleStringProperty(policyTopic);
        this.policySubTopic = new SimpleStringProperty(policySubTopic);
        this.policyQuestionText = new SimpleStringProperty(policyQuestionText);
        this.answerOptionA = new SimpleStringProperty(answerOptionA);
        this.answerOptionB = new SimpleStringProperty(answerOptionB);
        this.answerOptionC = new SimpleStringProperty(answerOptionC);
        this.answerOptionD = new SimpleStringProperty(answerOptionD);
        this.answerOptionE = new SimpleStringProperty(answerOptionE);
        this.correctAnswer = new SimpleIntegerProperty(correctAnswer);
    }

    public int getPolicyQuestionNumber() {
        return policyQuestionNumber.get();
    }

    public IntegerProperty policyQuestionNumberProperty() {
        return policyQuestionNumber;
    }

    // This way works otherwise get null errors
    public void setPolicyQuestionNumber(int policyQuestionNumber) {
            this.policyQuestionNumber = new SimpleIntegerProperty(policyQuestionNumber);
    }

    public String getPolicyTopic() {
        return policyTopic.get();
    }

    public StringProperty policyTopicProperty() {
        return policyTopic;
    }

    public void setPolicyTopic(String policyTopic) {
        this.policyTopic = new SimpleStringProperty(policyTopic);
    }

    public String getPolicySubTopic() {
        return policySubTopic.get();
    }

    public StringProperty policySubTopicProperty() {
        return policySubTopic;
    }

    public void setPolicySubTopic(String policySubTopic) {
        this.policySubTopic = new SimpleStringProperty(policySubTopic);
    }

    public String getPolicyQuestionText() {
        return policyQuestionText.get();
    }

    public StringProperty policyQuestionTextProperty() {
        return policyQuestionText;
    }

    public void setPolicyQuestionText(String policyQuestionText) {
        this.policyQuestionText = new SimpleStringProperty(policyQuestionText);
    }

    public String getAnswerOptionA() {
        return answerOptionA.get();
    }

    public StringProperty answerOptionAProperty() {
        return answerOptionA;
    }

    public void setAnswerOptionA(String answerOptionA) {
        this.answerOptionA = new SimpleStringProperty(answerOptionA);
    }

    public String getAnswerOptionB() {
        return answerOptionB.get();
    }

    public StringProperty answerOptionBProperty() {
        return answerOptionB;
    }

    public void setAnswerOptionB(String answerOptionB) {
        this.answerOptionB = new SimpleStringProperty(answerOptionB);
    }

    public String getAnswerOptionC() {
        return answerOptionC.get();
    }

    public StringProperty answerOptionCProperty() {
        return answerOptionC;
    }

    public void setAnswerOptionC(String answerOptionC) {
        this.answerOptionC = new SimpleStringProperty(answerOptionC);
    }

    public String getAnswerOptionD() {
        return answerOptionD.get();
    }

    public StringProperty answerOptionDProperty() {
        return answerOptionD;
    }

    public void setAnswerOptionD(String answerOptionD) {
        this.answerOptionD = new SimpleStringProperty(answerOptionD);
    }

    public String getAnswerOptionE() {
        return answerOptionE.get();
    }

    public StringProperty answerOptionEProperty() {
        return answerOptionE;
    }

    public void setAnswerOptionE(String answerOptionE) {
        this.answerOptionE = new SimpleStringProperty(answerOptionE);
    }

    public int getCorrectAnswer() {
        return correctAnswer.get();
    }

    public IntegerProperty correctAnswerProperty() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = new SimpleIntegerProperty(correctAnswer);
    }

    public PolicyQuestionModel(int policyQuestionNumber, String policyTopic, String policySubTopic)
    //public PolicyQuestionModel(String policyQuestionNumber, String policyTopic, String policySubTopic)
    {
        this.policyQuestionNumber = new SimpleIntegerProperty(policyQuestionNumber);
        this.policyTopic = new SimpleStringProperty(policyTopic);
        this.policySubTopic = new SimpleStringProperty(policySubTopic);
    }
}
