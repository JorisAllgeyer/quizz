package fr.doranco.myquizz.model;

import java.util.Collections;
import java.util.List;

public class QuestionBank {

    private List<Question> questionList;
    private int nextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        this.questionList = questionList;

        Collections.shuffle(questionList);
        nextQuestionIndex = 0;
    }

    public Question getNextQuestion() {

        if (nextQuestionIndex == questionList.size()) {
            nextQuestionIndex = 0;
        }

        return questionList.get(nextQuestionIndex++);
    }
}
