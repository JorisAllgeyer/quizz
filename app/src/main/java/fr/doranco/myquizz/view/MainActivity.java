package fr.doranco.myquizz.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.doranco.myquizz.entity.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SharedPreferences sharedPreferences;

    private TextView tvTitle;
    private EditText etName;
    private Button btnStart;
    private User User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        etName = findViewById(R.id.etName);
        btnStart = findViewById(R.id.btnStart);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        // Disable btnStart
        btnStart.setEnabled(false);


    }
}