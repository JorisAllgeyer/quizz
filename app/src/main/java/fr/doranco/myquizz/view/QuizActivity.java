package fr.doranco.myquizz.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import fr.doranco.myquizz.model.Question;
import fr.doranco.myquizz.model.QuestionBank;

public class QuizActivity extends AppCompatActivity implements IConst, View.OnClickListener {

    private static final String TAG = QuizActivity.class.getSimpleName();

    private TextView tvQuestion;
    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;

    private QuestionBank questionBank;
    private Question currentQuestion;
    private int maxQuestions;
    private int questionAsked;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        tvQuestion = findViewById(R.id.tvQuestion);
        btnAnswer1 = findViewById(R.id.btnA1);
        btnAnswer2 = findViewById(R.id.btnA2);
        btnAnswer3 = findViewById(R.id.btnA3);
        btnAnswer4 = findViewById(R.id.btnA4);

        questionBank = generateQuestions();

        score = 0;
        questionAsked = maxQuestions = 4;

        // Listeners
        btnAnswer1.setTag(0);
        btnAnswer2.setTag(1);
        btnAnswer3.setTag(2);
        btnAnswer4.setTag(3);
        btnAnswer1.setOnClickListener(this);
        btnAnswer2.setOnClickListener(this);
        btnAnswer3.setOnClickListener(this);
        btnAnswer4.setOnClickListener(this);

        currentQuestion = questionBank.getNextQuestion();
        this.displayCurrentQuestion(currentQuestion);
    }

    private void displayCurrentQuestion(Question currentQuestion) {
        tvQuestion.setText(currentQuestion.getQuestion());
        btnAnswer1.setText(currentQuestion.getChoiceList().get(0));
        btnAnswer2.setText(currentQuestion.getChoiceList().get(1));
        btnAnswer3.setText(currentQuestion.getChoiceList().get(2));
        btnAnswer4.setText(currentQuestion.getChoiceList().get(3));
    }

    @Override
    public void onClick(View view) {
        int responseIndex = (int) view.getTag();

        if (responseIndex == currentQuestion.getCorrectAnswerIndex()) {
            score++;
            Toast.makeText(QuizActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(QuizActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
        }

        if (--questionAsked == 0) {
            endQuiz();
        } else {
            currentQuestion = questionBank.getNextQuestion();
            displayCurrentQuestion(currentQuestion);
        }

    }

    private void endQuiz() {
        // TODO
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Quel est le pays le plus peuplé du monde ?",
                Arrays.asList("USA", "Chine", "Inde", "Indonésie"),1);

        Question question2 = new Question("Combien y'à-t-il de Pays dans l'UE ?",
                Arrays.asList("15", "24", "27", "32"),2);

        Question question3 = new Question("Quel est le créateur du système Android ?",
                Arrays.asList("Jake Wharton", "Steve Wozmiak", "Paul Smith", "Andy Rubin"),3);

        Question question4 = new Question("Quel est le premier président de la 4e République Française ?",
                Arrays.asList("Vicent Auriol", "René Coty", "Albert Lebrun", "Paul Doumer"),0);

        Question question5 = new Question("Quel est la plus petite République du Monde ?",
                Arrays.asList("Nauru", "Monaco", "Les Palaos", "Les Tuvalu"),1);

        Question question6 = new Question("Quelle est la langue la moins parlée au monde ?",
                Arrays.asList("L'artchi", "Le Silbo", "Rotokas", "le Piraha"),0);

        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5, question6));
    }

}