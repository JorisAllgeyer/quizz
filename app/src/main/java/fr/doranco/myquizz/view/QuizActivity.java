package fr.doranco.myquizz.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
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

        questionBank = QuestionBank.generateQuestions();
        maxQuestions = 4;

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(STATE_SCORE);
            questionAsked = savedInstanceState.getInt(STATE_QUESTION);
            currentQuestion = (Question) savedInstanceState.getSerializable(STATE_CURRENT_QUESTION);
        } else {
            score = 0;
            questionAsked = 4;
            currentQuestion = questionBank.getNextQuestion();
        }

        tvQuestion = findViewById(R.id.tvQuestion);
        btnAnswer1 = findViewById(R.id.btnA1);
        btnAnswer2 = findViewById(R.id.btnA2);
        btnAnswer3 = findViewById(R.id.btnA3);
        btnAnswer4 = findViewById(R.id.btnA4);

        // Listeners
        btnAnswer1.setTag(0);
        btnAnswer2.setTag(1);
        btnAnswer3.setTag(2);
        btnAnswer4.setTag(3);
        btnAnswer1.setOnClickListener(this);
        btnAnswer2.setOnClickListener(this);
        btnAnswer3.setOnClickListener(this);
        btnAnswer4.setOnClickListener(this);

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
        // View is the page, you can get only one tag per view.
        int responseIndex = (int) view.getTag();

        if (responseIndex == currentQuestion.getCorrectAnswerIndex()) {
            score++;
            Toast.makeText(QuizActivity.this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(QuizActivity.this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (--questionAsked == 0) {
                    endQuiz();
                } else {
                    currentQuestion = questionBank.getNextQuestion();
                    displayCurrentQuestion(currentQuestion);
                }

            }
        }, 2000);
    }

    private void endQuiz() {
        // TODO
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz terminé")
                .setMessage("Votre score est de: " + score + "/" + maxQuestions)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_SCORE_PARAM, score);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(STATE_SCORE, score);
        outState.putInt(STATE_QUESTION, questionAsked);
        outState.putSerializable(STATE_CURRENT_QUESTION, currentQuestion);
        super.onSaveInstanceState(outState);
    }

}