package fr.doranco.myquizz.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private String question;
    private List<String> choiceList;
    private int correctAnswerIndex;

    public Question(String question, List<String> choiceList, int answerIndex) {
        this.question = question;
        this.choiceList = choiceList;
        this.correctAnswerIndex = answerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getChoiceList() {
        return choiceList;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

}
