package fr.doranco.myquizz.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.doranco.myquizz.entity.User;

public class MainActivity extends AppCompatActivity implements IConst {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 1;

    private SharedPreferences sharedPreferences;

    private TextView tvTitle;
    private EditText etName;
    private Button btnStart;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Elements
        tvTitle = findViewById(R.id.tvTitle);
        etName = findViewById(R.id.etName);
        btnStart = findViewById(R.id.btnStart);

        // Preferences
        sharedPreferences = getPreferences(MODE_PRIVATE);

        // Disable btnStart
        btnStart.setEnabled(false);
        user = new User();

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // One line verification
                btnStart.setEnabled(!charSequence.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String userName = sharedPreferences.getString(PREF_KEY_USER_NAME, "");
        int score = sharedPreferences.getInt(PREF_KEY_USER_SCORE, 0);

        if (userName.isEmpty()) {
            tvTitle.setText("Bonjour, veuillez saisir votre nom:");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Bonjour ").append(userName).append(", votre dernier score: ").append(score);

            tvTitle.setText(sb.toString());
            etName.setText(userName);
        }
    }

    public void onClickStart(View v) {
        String userName = etName.getText().toString();

        user.setName(userName);
        sharedPreferences.edit().putString(PREF_KEY_USER_NAME, userName).apply();

        Intent intentQuizActivity = new Intent(MainActivity.this, QuizActivity.class);
        startActivityForResult(intentQuizActivity, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            int score = data.getIntExtra(EXTRA_SCORE_PARAM, 0);
            sharedPreferences.edit().putInt(PREF_KEY_USER_SCORE, score).apply();
        }
    }
}