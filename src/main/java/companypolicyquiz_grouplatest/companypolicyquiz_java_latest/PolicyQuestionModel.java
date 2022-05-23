package companypolicyquiz_grouplatest.companypolicyquiz_java_latest;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PolicyQuestionModel {

    private IntegerProperty policyQuestionNumberProp;
    private StringProperty policyTopicProp;
    private StringProperty policySubTopicProp;
    private StringProperty policyQuestionTextProp;
    private StringProperty answerOptionAProp;
    private StringProperty answerOptionBProp;
    private StringProperty answerOptionCProp;
    private StringProperty answerOptionDProp;
    private StringProperty answerOptionEProp;
    private IntegerProperty correctAnswerProp;

    public PolicyQuestionModel() {
        this(1, "", "");
    }

    public PolicyQuestionModel(int policyQuestionNumber, String policyTopic, String policySubTopic) {
        this(policyQuestionNumber, policyTopic, policySubTopic, "", "", "", "", "", "", 1);
    }

    public PolicyQuestionModel(
            int policyQuestionNumber,
            String policyTopic,
            String policySubTopic,
            String policyQuestionText,
            String answerOptionA,
            String answerOptionB,
            String answerOptionC,
            String answerOptionD,
            String answerOptionE,
            int correctAnswer) {
        policyQuestionNumberProp = new SimpleIntegerProperty(policyQuestionNumber);
        policyTopicProp = new SimpleStringProperty(policyTopic);
        policySubTopicProp = new SimpleStringProperty(policySubTopic);
        policyQuestionTextProp = new SimpleStringProperty(policyQuestionText);
        answerOptionAProp = new SimpleStringProperty(answerOptionA);
        answerOptionBProp = new SimpleStringProperty(answerOptionB);
        answerOptionCProp = new SimpleStringProperty(answerOptionC);
        answerOptionDProp = new SimpleStringProperty(answerOptionD);
        answerOptionEProp = new SimpleStringProperty(answerOptionE);
        correctAnswerProp = new SimpleIntegerProperty(correctAnswer);
    }

    public int getPolicyQuestionNumber() {
        return policyQuestionNumberProp.get();
    }

    public void setPolicyQuestionNumber(int policyQuestionNumber) {
        this.policyQuestionNumberProp.set(policyQuestionNumber);
    }

    public IntegerProperty policyQuestionNumberProperty() {
        return policyQuestionNumberProp;
    }

    public String getPolicyTopic() {
        return policyTopicProp.get();
    }

    public void setPolicyTopic(String policyTopic) {
        this.policyTopicProp.set(policyTopic);
    }

    public StringProperty policyTopicProperty() {
        return policyTopicProp;
    }

    public String getPolicySubTopic() {
        return policySubTopicProp.get();
    }

    public void setPolicySubTopic(String policySubTopic) {
        this.policySubTopicProp.set(policySubTopic);
    }

    public StringProperty policySubTopicProperty() {
        return policySubTopicProp;
    }

    public String getPolicyQuestionText() {
        return policyQuestionTextProp.get();
    }

    public void setPolicyQuestionText(String policyQuestionText) {
        this.policyQuestionTextProp.set(policyQuestionText);
    }

    public StringProperty policyQuestionTextProperty() {
        return policyQuestionTextProp;
    }

    public String getAnswerOptionA() {
        return answerOptionAProp.get();
    }

    public void setAnswerOptionA(String answerOptionA) {
        this.answerOptionAProp.set(answerOptionA);
    }

    public StringProperty answerOptionAProperty() {
        return answerOptionAProp;
    }

    public String getAnswerOptionB() {
        return answerOptionBProp.get();
    }

    public void setAnswerOptionB(String answerOptionB) {
        this.answerOptionBProp.set(answerOptionB);
    }

    public StringProperty answerOptionBProperty() {
        return answerOptionBProp;
    }

    public String getAnswerOptionC() {
        return answerOptionCProp.get();
    }

    public void setAnswerOptionC(String answerOptionC) {
        this.answerOptionCProp.set(answerOptionC);
    }

    public StringProperty answerOptionCProperty() {
        return answerOptionCProp;
    }

    public String getAnswerOptionD() {
        return answerOptionDProp.get();
    }

    public void setAnswerOptionD(String answerOptionD) {
        this.answerOptionDProp.set(answerOptionD);
    }

    public StringProperty answerOptionDProperty() {
        return answerOptionDProp;
    }

    public String getAnswerOptionE() {
        return answerOptionEProp.get();
    }

    public void setAnswerOptionE(String answerOptionE) {
        this.answerOptionEProp.set(answerOptionE);
    }

    public StringProperty answerOptionEProperty() {
        return answerOptionEProp;
    }

    public int getCorrectAnswer() {
        return correctAnswerProp.get();
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswerProp.set(correctAnswer);
    }

    public IntegerProperty correctAnswerProperty() {
        return correctAnswerProp;
    }

}
