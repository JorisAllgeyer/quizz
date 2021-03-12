package fr.doranco.myquizz.model;

import java.util.Arrays;
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

    public static QuestionBank generateQuestions() {
        Question question1 = new Question("Quel est le pays le plus peuplé du monde ?",
                Arrays.asList("USA", "Chine", "Inde", "Indonésie"),1);

        Question question2 = new Question("Combien y'à-t-il de Pays dans l'UE ?",
                Arrays.asList("15", "24", "27", "32"),2);

        Question question3 = new Question("Quel est le créateur du système Android ?",
                Arrays.asList("Jake Wharton", "Steve Wozmiak", "Paul Smith", "Andy Rubin"),3);

        Question question4 = new Question("Quel est le premier président de la 4e République Française ?",
                Arrays.asList("Vicent Auriol", "René Coty", "Albert Lebrun", "Paul Doumer"),0);

        Question question5 = new Question("Quel est la plus petite République du Monde ?",
                Arrays.asList("Nauru", "Monaco", "Les Palaos", "Les Tuvalu"),0);

        Question question6 = new Question("Quelle est la langue la moins parlée au monde ?",
                Arrays.asList("L'artchi", "Le Silbo", "Rotokas", "le Piraha"),0);

        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5, question6));
    }
}
