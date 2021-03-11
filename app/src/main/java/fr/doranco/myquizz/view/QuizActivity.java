package fr.doranco.myquizz.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity implements IConst {

    private static final String TAG = QuizActivity.class.getSimpleName();

    private TextView tvQuestion;
    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        tvQuestion = findViewById(R.id.tvQuestion);
        btnAnswer1 = findViewById(R.id.btnA1);
        btnAnswer2 = findViewById(R.id.btnA2);
        btnAnswer3 = findViewById(R.id.btnA3);
        btnAnswer4 = findViewById(R.id.btnA4);


    }
}