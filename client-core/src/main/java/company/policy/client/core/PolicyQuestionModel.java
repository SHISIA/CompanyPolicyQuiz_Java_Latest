package company.policy.client.core;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PolicyQuestionModel implements Comparable<PolicyQuestionModel> {

    private StringProperty staffNameProp;
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
    private IntegerProperty givenAnswerProp;

    public PolicyQuestionModel() {
        this(0, "", "");
    }

    public PolicyQuestionModel(int policyQuestionNumber, String policyTopic, String policySubTopic) {
        this(policyQuestionNumber, policyTopic, policySubTopic, "", "", "", "", "", "", 0);
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
        staffNameProp = new SimpleStringProperty("");
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
        givenAnswerProp = new SimpleIntegerProperty();
    }

    public String getStaffName() {
        return staffNameProp.get();
    }

    public void setStaffName(String staffName) {
        this.staffNameProp.set(staffName);
    }

    public StringProperty staffNameProperty() {
        return staffNameProp;
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

    public int getGivenAnswer() {
        return givenAnswerProp.get();
    }

    public void setGivenAnswer(int givenAnswer) {
        givenAnswerProp.set(givenAnswer);
    }

    public IntegerProperty givenAnswerProperty() {
        return givenAnswerProp;
    }

    private int attemptedCount;

    public int getAttemptedCount() {
        return attemptedCount;
    }

    public void setAttemptedCount(int attemptedCount) {
        this.attemptedCount = attemptedCount;
    }

    private int answeredCorrectCount;

    public int getAnsweredCorrectCount() {
        return answeredCorrectCount;
    }

    public void setAnsweredCorrectCount(int answeredCorrectCount) {
        this.answeredCorrectCount = answeredCorrectCount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.staffNameProp.get());
        hash = 59 * hash + Objects.hashCode(this.policyQuestionNumberProp.get());
        hash = 59 * hash + Objects.hashCode(this.policyTopicProp.get());
        hash = 59 * hash + Objects.hashCode(this.policySubTopicProp.get());
        hash = 59 * hash + Objects.hashCode(this.policyQuestionTextProp.get());
        hash = 59 * hash + Objects.hashCode(this.answerOptionAProp.get());
        hash = 59 * hash + Objects.hashCode(this.answerOptionBProp.get());
        hash = 59 * hash + Objects.hashCode(this.answerOptionCProp.get());
        hash = 59 * hash + Objects.hashCode(this.answerOptionDProp.get());
        hash = 59 * hash + Objects.hashCode(this.answerOptionEProp.get());
        hash = 59 * hash + Objects.hashCode(this.correctAnswerProp.get());
        hash = 59 * hash + Objects.hashCode(this.givenAnswerProp.get());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PolicyQuestionModel other = (PolicyQuestionModel) obj;
        if (!Objects.equals(this.staffNameProp.get(), other.staffNameProp.get())) {
            return false;
        }
        if (!Objects.equals(this.policyQuestionNumberProp.get(), other.policyQuestionNumberProp.get())) {
            return false;
        }
        if (!Objects.equals(this.policyTopicProp.get(), other.policyTopicProp.get())) {
            return false;
        }
        if (!Objects.equals(this.policySubTopicProp.get(), other.policySubTopicProp.get())) {
            return false;
        }
        if (!Objects.equals(this.policyQuestionTextProp.get(), other.policyQuestionTextProp.get())) {
            return false;
        }
        if (!Objects.equals(this.answerOptionAProp.get(), other.answerOptionAProp.get())) {
            return false;
        }
        if (!Objects.equals(this.answerOptionBProp.get(), other.answerOptionBProp.get())) {
            return false;
        }
        if (!Objects.equals(this.answerOptionCProp.get(), other.answerOptionCProp.get())) {
            return false;
        }
        if (!Objects.equals(this.answerOptionDProp.get(), other.answerOptionDProp.get())) {
            return false;
        }
        if (!Objects.equals(this.answerOptionEProp.get(), other.answerOptionEProp.get())) {
            return false;
        }
        if (!Objects.equals(this.correctAnswerProp.get(), other.correctAnswerProp.get())) {
            return false;
        }
        return Objects.equals(this.givenAnswerProp.get(), other.givenAnswerProp.get());
    }

    @Override
    public int compareTo(PolicyQuestionModel that) {
        return Comparator.comparing(PolicyQuestionModel::getPolicyQuestionNumber).compare(this, that);
    }

    @Override
    public String toString() {
        return new AsString().apply(this);
    }

    public static PolicyQuestionModel valueOf(String val) {
        return new AsModel().apply(val);
    }

    private static class AsString implements Function<PolicyQuestionModel, String> {

        @Override
        public String apply(PolicyQuestionModel model) {

            return List.of(model.getStaffName(),
                    Integer.toString(model.getPolicyQuestionNumber()),
                    model.getPolicyTopic(),
                    model.getPolicySubTopic(),
                    model.getPolicyQuestionText(),
                    model.getAnswerOptionA(),
                    model.getAnswerOptionB(),
                    model.getAnswerOptionC(),
                    model.getAnswerOptionD(),
                    model.getAnswerOptionE(),
                    Integer.toString(model.getCorrectAnswer()),
                    Integer.toString(model.getGivenAnswer()))
                    .stream()
                    .collect(Collectors.joining(System.lineSeparator()));
        }

    }

    private static class AsModel implements Function<String, PolicyQuestionModel> {

        @Override
        public PolicyQuestionModel apply(String s) {
            PolicyQuestionModel model = new PolicyQuestionModel();
            String[] fields = s.split("\n");

            if (fields.length == 12) {
                model.setStaffName(fields[0]);
                model.setPolicyQuestionNumber(Integer.parseInt(fields[1]));
                model.setPolicyTopic(fields[2]);
                model.setPolicySubTopic(fields[3]);
                model.setPolicyQuestionText(fields[4]);
                model.setAnswerOptionA(fields[5]);
                model.setAnswerOptionB(fields[6]);
                model.setAnswerOptionC(fields[7]);
                model.setAnswerOptionD(fields[8]);
                model.setAnswerOptionE(fields[9]);
                model.setCorrectAnswer(Integer.parseInt(fields[10]));
                model.setGivenAnswer(Integer.parseInt(fields[11]));
            }

            return model;
        }

    }

}
